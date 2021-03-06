package code;

/*
 * Copyright (c) 2013, Erika Nana
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Project 1 nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Erika Nana ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Erika Nana BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class represents an adjacency list.
 * @author Erika Nana
 */
public class AList {
	
	/** The out vertices. */
	HashMap<Vertex,HashSet<Vertex>> outVertices;
		
	/**Edges for undirected graph*/
	ArrayList<Arc> undirectedEdges; //inefficient space wise
	
	/** The num of edges. */
	int numOfEdges;
	
	/**
	 * Instantiates a new adjacency list.
	 */
	public AList() {
		outVertices = new HashMap<Vertex,HashSet<Vertex>>();
		undirectedEdges = new ArrayList<Arc>();
	}
	
	/**
	 * Adds the vertex.
	 *
	 * @param vertex the vertex
	 */
	public void addVertex(Vertex vertex) {
		//add start vertex to the HashMaps
		if (!outVertices.containsKey(vertex)) {
			HashSet<Vertex> tree = new HashSet<Vertex>();
			//initialize the degree
			vertex.setAnnotation("undirectedDegree", 0);
			outVertices.put(vertex,tree);
		}
	}
	
	/**
	 * Adds the edge.  Assumes that each edge is unique.
	 *
	 * @param start the start vertex
	 * @param end the end vertex
	 */
	public void addEdge(Vertex start, Vertex end) {
		//check if an edge exists in reverse direction.  won't work if the same edges
		//are added more than once
		HashSet<Vertex> endPointsOfEndpoint = outVertices.get(end);
		if (endPointsOfEndpoint.contains(start)) {
			//there is an undirected edge between the two
			Arc edge = new Arc(start,end);
			undirectedEdges.add(edge);
			//update degree
			//System.out.println("start is:  " + start);
			//System.out.println("end is:  " + end);
			start.updateUndirectedDegree();
			end.updateUndirectedDegree();

		}
		//this assumes that vertices to be connected already exist in the adj. tree
		if (outVertices.containsKey(start)) {
			HashSet<Vertex>set = outVertices.get(start);
			//only add edge if it doesn't exist
			if (!set.contains(end)) {
				set.add(end);
				numOfEdges++;
			}
		}
	}
	
	/**
	 * Delete vertex.
	 *
	 * @param vertex the vertex to be deleted
	 */
	public void deleteVertex(Vertex vertex) {
		//remove vertex and update 
		if (outVertices.containsKey(vertex)) {
			//check if that vertex has outgoing edges
			numOfEdges = numOfEdges - outVertices.get(vertex).size();
			outVertices.remove(vertex);
			//get the tree from each value in HashMap and delete the vertex from that tree
			for (HashSet<Vertex> tree: outVertices.values()) {
				if (tree.contains(vertex)){
					tree.remove(vertex);
					numOfEdges--;
				}
			}
		}
	}
	
	/**
	 * Delete edge.
	 *
	 * @param start the start vertex
	 * @param end the end vertex
	 */
	public void deleteEdge(Vertex start, Vertex end) {
		HashSet<Vertex> outSet = outVertices.get(start);
		outSet.remove(end);
		numOfEdges--;
	}
	
	/**
	 * Gets the out degree.
	 *
	 * @param vertex the vertex to get the out degree from
	 * @return the out degree of the vertex
	 */
	public int getOutDegree(Vertex vertex) {
		HashSet<Vertex> set = outVertices.get(vertex);
		return set.size();
	}
	
	/**
	 * Gets the in degree.
	 *
	 * @param vertex the vertex to get the in degree from
	 * @return the in degree
	 */
	public int getInDegree(Vertex vertex) {
		int counter = 0;
		Set<Vertex> keys = outVertices.keySet();
		for (Iterator<Vertex> i = keys.iterator(); i.hasNext();) {
			HashSet<Vertex> set = outVertices.get(i.next());
			if(set.contains(vertex)) {
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * Gets the HashMap representation of the adjacency list
	 *
	 * @return the HashMap
	 */
	public HashMap<Vertex,HashSet<Vertex>> getMap() {
		return outVertices;
	}
	
	/**
	 * Gets the num of edges.
	 *
	 * @return the num of edges
	 */
	public int getNumOfEdges() {
		return numOfEdges;
	}
	
	/**
	 * Gets the num of vertices.
	 *
	 * @return the num of vertices
	 */
	public int getNumOfVertices() {
		return outVertices.size();
	}
	
	/**
	 * Reverse edge.
	 *
	 * @param startVertex the start vertex of the edge to be reversed
	 * @param endVertex the end vertex of the edge to be reversed
	 */
	public void reverseEdge(Vertex startVertex, Vertex endVertex) {
		HashSet<Vertex> list = outVertices.get(startVertex);
		list.remove(endVertex);
		HashSet<Vertex> updated = outVertices.get(endVertex);
		updated.add(startVertex);
	}
	
	public double getReciprocity() {
		Set<Vertex> vertices = outVertices.keySet();
		Iterator<Vertex> iterator = vertices.iterator();
		int counter = 0;
		while (iterator.hasNext()) {
			Vertex vertex = iterator.next();
			counter = counter + getUndirectedDegree(vertex);
		}
		return (double) counter/numOfEdges;
	}

	public int getUndirectedDegree(Vertex vertex) {
		return vertex.getUndirectedDegree();
	}
	
	public double getDegreeCorrelation() {
		//get the s1 summation
		double s1 = 0;
		double s2 = 0;
		double s3 = 0;
		Set<Vertex> vertices = outVertices.keySet();
		Iterator<Vertex> iterator = vertices.iterator();
		int counter = 0;
		while (iterator.hasNext()) {
			Vertex currentVertex = iterator.next();
			s1 = s1 + getUndirectedDegree(currentVertex);
			s2 = s2 + Math.pow(getUndirectedDegree(currentVertex), 2);
			s3 = s3 + Math.pow(getUndirectedDegree(currentVertex), 3);
			counter++;
		}

		//get the se summation
		double se = 0;
		counter = 0;
		for (Arc edge: undirectedEdges) {
			counter++;
			double startDegree = getUndirectedDegree(edge.getStartVertex());
			double endDegree = getUndirectedDegree(edge.getEndVertex());
			System.out.println("start vertex:  " + edge.getStartVertex());
			System.out.println("end vertex:  " + edge.getEndVertex());
			System.out.println("     start degree:  " + startDegree);
			System.out.println("     end degree:  " + endDegree);
			double product = startDegree * endDegree;
			System.out.println("        product:  " + product);
			se = se + product;
			System.out.println("        se now:  " + se);
		}
		System.out.println("counter from undirected edges:  " + counter);
		se = 2*se;
		System.out.println("se:  " + se);
		//calculate r
		double numerator = (s1*se) - (Math.pow(s2, 2));
		double denominator = (s1 * s3) - Math.pow(s2, 2);
		return numerator/denominator;
	}
	
/*	public int getUndirectedDegreeTwo(Vertex vertex) {
		int degree = 0;
		HashSet<Vertex> endpoints = outVertices.get(vertex);
		Iterator<Vertex> epIterator = endpoints.iterator();
		while (epIterator.hasNext()) {
			Vertex endpoint = epIterator.next();
			HashSet<Vertex> set = outVertices.get(endpoint);
			if (set.contains(vertex)) {
				degree++;
			}
		}
		return degree;
	}*/
	public double getClusteringCoefficient() {
		Set<Vertex> vertices = outVertices.keySet();
		Iterator<Vertex> iterator = vertices.iterator();
		int counter = 0;
		int pathsOfLengthTwo = 0;
		while (iterator.hasNext()) {
			Vertex currentVertex = iterator.next();
			pathsOfLengthTwo = pathsOfLengthTwo + getUndirectedDegree(currentVertex)/2;
			HashSet<Vertex> neighbors = outVertices.get(currentVertex);
			ArrayList<Vertex> endpoints = new ArrayList<Vertex>();
			endpoints.addAll(neighbors);
			//check for each neighbor j, if there's an edge for neighbor l > j
			int first;
			int second;
			for (int i = 0; i < endpoints.size(); i++) {
				first = i;
				second = i+1;
				//check if edge for neighbor l > j
				while (second < endpoints.size()) {
					//compare first and second
					Vertex secondVertex = endpoints.get(second);
					Vertex firstVertex = endpoints.get(first);
					if (outVertices.get(secondVertex).contains(firstVertex)) {
						counter++;
					}
					second++;
				}
			}
		}
		System.out.println("counter: "  + counter);
		pathsOfLengthTwo = 2 * pathsOfLengthTwo;
		double numberOfTriads = counter / 3;
		System.out.println("number of paths:  " + pathsOfLengthTwo);
		System.out.println("number of triads:  " + numberOfTriads);
		return numberOfTriads/pathsOfLengthTwo;
	}
	
	public double[] getGeodesicDistance() {
		double [] geoStats = new double [2];
		//mean geodesic distance 
/*		average length of the shortest path between each pair of vertices in the graph
		perform BFS starting at each vertex in network in turn
			use the better implementation
		Algorithm: (uses two arrays of n elements)
		iterative djikstra: VE lg V
		iterative BFS: 
			*/
		return geoStats;
	}
	
	public int numberOfUndirectedEdges() {
		return undirectedEdges.size();
	}
	
	public void printAList() {
		Iterator<Vertex> vertices = outVertices.keySet().iterator();
		while (vertices.hasNext()) {
			Vertex vertex = vertices.next();
			System.out.println("vertex:  " + vertex);
			HashSet<Vertex> endpoints = outVertices.get(vertex);
			Iterator<Vertex> iterator = endpoints.iterator();
			while (iterator.hasNext()) {
				Vertex endpoint = iterator.next();
				System.out.print("--->" + endpoint);
			}
			System.out.println("");
			System.out.println("");
		}
	}
}