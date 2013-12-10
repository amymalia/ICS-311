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
	
	/** The num of edges. */
	int numOfEdges;
	
	/**
	 * Instantiates a new adjacency list.
	 */
	public AList() {
		outVertices = new HashMap<Vertex,HashSet<Vertex>>();
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
			outVertices.put(vertex,tree);
		}
	}
	
	/**
	 * Adds the edge.
	 *
	 * @param start the start vertex
	 * @param end the end vertex
	 */
	public void addEdge(Vertex start, Vertex end) {
		//this assumes that vertices to be connected already exist in the adj. tree

		if (outVertices.containsKey(start)) {
			HashSet<Vertex>set = outVertices.get(start);
			set.add(end);
			numOfEdges++;
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
	
	//inefficient
	public double getReciprocity() {
		Set<Vertex> keys = outVertices.keySet();
		Iterator<Vertex> iterator = keys.iterator();
		int counter = 0;
		while(iterator.hasNext()) {
			Vertex key = iterator.next();
			HashSet<Vertex> endpoints = outVertices.get(key);
			Iterator<Vertex> endIterator = endpoints.iterator();
			//check if endpoints of current vertex are reciprocated
			while (endIterator.hasNext()) {
				Vertex endPoint = endIterator.next();
				HashSet<Vertex> endPointsOfEndpoints = outVertices.get(endPoint);
				if (endPointsOfEndpoints.contains(key)) {
					counter++;
				}
			}
		}
		return (double) counter/numOfEdges;
	}
	
	//also inefficient
	public int getUndirectedDegree(Vertex vertex) {
		HashSet<Vertex> endpoints = outVertices.get(vertex);
		//check if there's an edge from endpoint to vertex
		Iterator<Vertex> iterator = endpoints.iterator();
		int counter = 0;
		while (iterator.hasNext()) {
			Vertex endPoint = iterator.next();
			HashSet<Vertex> endPointsOfEndPoint = outVertices.get(endPoint);
			if (endPointsOfEndPoint.contains(vertex)) {
				counter++;
			}
		}
		return counter++;
	}
}