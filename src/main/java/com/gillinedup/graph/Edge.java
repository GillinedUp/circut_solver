package com.gillinedup.graph;

import java.util.ArrayList;
import java.util.List;

public class Edge {
	private final Vertex source;
	private final Vertex destination;
	private double weight;
	private List<CurrentDirection> currents;
	private boolean isVoltage;
	private int voltageDirection;

	public Edge(Vertex source, Vertex destination) {
		this.source = source;
		this.destination = destination;
		this.weight = 0.0;
		this.currents = new ArrayList<>();
		this.isVoltage = false;
		this.voltageDirection = 0; // - -> + == > 0; + -> - == < 0
	}

	public Edge(Vertex source, Vertex destination, double weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
        this.currents = new ArrayList<>();
        this.isVoltage = false;
        this.voltageDirection = 0;
	}

    public int getVoltageDirection() {
        return voltageDirection;
    }

    public void setVoltageDirection(int voltageDirection) {
        this.voltageDirection = voltageDirection;
    }

    public Vertex getDestination() {
		return destination;
	}

	public Vertex getSource() {
		return source;
	}

	public void setWeight(double weight) {
	    this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public List<CurrentDirection> getCurrents() {
        return currents;
    }

    public void setCurrents(List<CurrentDirection> currents) {
        this.currents = currents;
    }

    public boolean isVoltage() {
        return isVoltage;
    }

    public void setVoltage() {
	    isVoltage = true;
    }

    @Override
	public String toString() {
		return source + "-" + destination;
	}

	@Override
	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
//		result = prime * result + ((source == null) ? 0 : source.hashCode());
//		return result;
		int x = source.getId();
		int y = destination.getId();
		return ((x + y)*(x + y + 1))/2 + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

}