package com.spdb.scenicrouteplanner.model;

import com.spdb.scenicrouteplanner.lib.GeoCoords;

import java.util.ArrayList;

public class Node {
    private long id;
    private GeoCoords geoCoords;
    private ArrayList<Edge> edges = new ArrayList<>();

    public Node() {
    }

    public Node(long id, GeoCoords geoCoords) {
        this.id = id;
        this.geoCoords = geoCoords;
    }

    public Node(long id, GeoCoords geoCoords, ArrayList<Edge> edges) {
        this.id = id;
        this.geoCoords = geoCoords;
        this.edges = edges;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GeoCoords getGeoCoords() {
        return geoCoords;
    }

    public void setGeoCoords(GeoCoords geoCoords) {
        this.geoCoords = geoCoords;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Edge> getOutgoingEdges() {
        ArrayList<Edge> outgoingEdges = new ArrayList<>();
        for (Edge e : edges) {
            if (e.getEndNode() != this)
                outgoingEdges.add(e);
        }
        return outgoingEdges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
}
