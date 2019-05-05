package com.example.sudoku;

public class Node {

    public Node Left;
    public Node Right;
    public Node Up;
    public Node Down;
    public Node Col;

    public int Name;
    public int Size;

    public Node(int n, int s){
        Left = this;
        Right = this;
        Up = this;
        Down = this;
        Col = this;
        Name = n;
        Size = s;
    }
}
