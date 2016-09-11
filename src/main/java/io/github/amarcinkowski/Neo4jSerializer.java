package io.github.amarcinkowski;

import java.io.File;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Neo4jSerializer {

	final static Logger logger = LoggerFactory.getLogger(Neo4jSerializer.class);
	static Label label = Label.label("object");

	private static enum RelTypes implements RelationshipType {
		CONTAINS, PARENT
	}

	static GraphDatabaseService graphDb;

	private static String recursiveVisit(Node rootNode, String s) {
		s += String.format("%1s > ", rootNode.getProperty("name").toString().toUpperCase());
		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}
		String n = ">";
		if (rootNode.hasRelationship(RelTypes.PARENT)) {
			Iterator<Relationship> i = rootNode.getRelationships(Direction.INCOMING, RelTypes.PARENT).iterator();
			while (i.hasNext()) {
				Relationship r = i.next();
				/* Node en = (Node) r.getEndNode(); */
				Node sn = (Node) r.getStartNode();
				if (sn != null) {
					n += recursiveVisit(sn, s);
				}
			}
		}
		s += "\n" + n;
		return s;
	}

	public static void fillDB(Universe universe) {
		startDB();
		try (Transaction tx = graphDb.beginTx()) {

			graphDb.execute("MATCH (n) DETACH DELETE n");

			for (CelestialObject co : universe) {
				Node node = graphDb.createNode(label);
				String name = co.name.get("pl");
				node.setProperty("name", name);
				logger.trace("Adding " + co.name.get("pl"));
				if (co.parent != null) {
					node.setProperty("parent", co.parent);
				}
			}

			try (ResourceIterator<Node> cos = graphDb.findNodes(label)) {
				while (cos.hasNext()) {
					Node coNode = cos.next();
					if (!coNode.hasProperty("parent")) {
						continue;
					}
					Object prop = coNode.getProperty("parent");
					Node coParentNode = graphDb.findNode(label, "name", prop);
					if (coParentNode == null) {
						continue;
					}
					/* Relationship relationship = */coNode.createRelationshipTo(coParentNode, RelTypes.PARENT);
					/* Relationship relationship = */coParentNode.createRelationshipTo(coNode, RelTypes.CONTAINS);
				}
			}
			visit();
			tx.success();
		}

		graphDb.shutdown();

	}

	private static void visit() {
		Node rootNode = graphDb.findNode(label, "name", "Wszechświat");
		String s = recursiveVisit(rootNode, "");
		System.out.println(s);
	}

	private static void startDB() {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(new File("target/neo4jdb"))
				.setConfig(GraphDatabaseSettings.pagecache_memory, "512M")
				.setConfig(GraphDatabaseSettings.string_block_size, "60")
				.setConfig(GraphDatabaseSettings.array_block_size, "300").newGraphDatabase();
	}
}
