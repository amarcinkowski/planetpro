package io.github.amarcinkowski;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

public class Neo4jSerializer {

	private static enum RelTypes implements RelationshipType {
		CONTAINS
	}

	static GraphDatabaseService graphDb = new GraphDatabaseFactory()
			.newEmbeddedDatabaseBuilder(new File("target/neo4jdb"))
			.setConfig(GraphDatabaseSettings.pagecache_memory, "512M")
			.setConfig(GraphDatabaseSettings.string_block_size, "60")
			.setConfig(GraphDatabaseSettings.array_block_size, "300").newGraphDatabase();

	public static void main(String[] args) {
		Node firstNode;
		Node secondNode;
		Relationship relationship;

		try (Transaction tx = graphDb.beginTx()) {

			firstNode = graphDb.createNode(Label.label("object"));
			firstNode.setProperty("name", "Sun");
			secondNode = graphDb.createNode(Label.label("object"));
			secondNode.setProperty("name", "Earth");

			relationship = firstNode.createRelationshipTo(secondNode, RelTypes.CONTAINS);
			relationship.setProperty("name", "system");

			System.out.print(firstNode.getProperty("name"));
			System.out.print(relationship.getProperty("name"));
			System.out.print(secondNode.getProperty("name"));
			// do your stuff
			try (ResourceIterator<Node> users = graphDb.findNodes(Label.label("object"), "name", "Earth")) {
				while (users.hasNext()) {
					System.out.println(users.next());
				}
			}
			tx.success();

		}

		graphDb.shutdown();
	}

}
