package com.teamuniverse.drtmobile.support;

public class IncidentInfoList {
	Node	head	= null;
	Node	current	= null;
	
	public IncidentInfoList(String firstKey, Object firstValue) {
		head = new Node(0, firstKey, firstValue, null);
		current = head;
	}
	
	public void addInfo(String key, Object value) {
		current = new Node(size(), key, value, current);
	}
	
	public Node get(int index) {
		Node cursor = head;
		for (int i = 0; i < index; i++) {
			cursor = cursor.getChild();
		}
		return cursor;
	}
	
	public Node get(String key) {
		Node cursor = head;
		for (int i = 0; i < size(); i++) {
			if (cursor.getKey().equals(key)) {
				return cursor;
			} else cursor = cursor.getChild();
		}
		return null;
	}
	
	public Node getNext() {
		current = current.getChild();
		return current;
	}
	
	public boolean hasNext() {
		return current.hasChild();
	}
	
	public int size() {
		int i = 0;
		Node cursor = head;
		while (cursor != null) {
			cursor = cursor.getChild();
			i++;
		}
		return i;
	}
	
	class Node {
		int		num;
		String	key;
		Object	value;
		Node	parent;
		Node	child;
		
		private Node(int num, String key, Object value, Node parent) {
			this.num = num;
			this.key = key;
			this.value = value;
			this.parent = parent.setChild(this);
			this.child = null;
		}
		
		private Node setChild(Node child) {
			this.child = child;
			return this;
		}
		
		private Node getChild() {
			return child;
		}
		
		private boolean hasChild() {
			return child != null;
		}
		
		public String getKey() {
			return key;
		}
		
		public Object getValue() {
			return value;
		}
	}
}
