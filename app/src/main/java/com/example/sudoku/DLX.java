package com.example.sudoku;

import com.example.sudoku.Node;

import java.util.LinkedList;
public class DLX {      //init

    private Node Head;
    private Node[] ColHead;
    private LinkedList<Node> cur;
    private int answer;
    private int []cnt;
    private LinkedList<LinkedList<Node>> res = new LinkedList<>();

    public DLX(boolean[][] judge,int row, int col){

        cur = new LinkedList<>();
        ColHead = new Node[col+1];
        Node[] ColTail = new Node[col+1];
        Head = new Node(0,-1);
        answer = 0;
        //skip = 0;
        //next = n;

        cnt = new int[col+1];
        for(int i=0;i<col+1;i++)cnt[i]=0;

        Node root = Head;

        for(int i=1;i<col+1;i++){
            Node tmp = new Node(i,0);
            ColHead[i] = tmp;
            ColTail[i] = tmp;
            root.Right = tmp;
            tmp.Left = root;
            root = tmp;
        }
        root.Right = Head;
        Head.Left = root;
        for(int i=1;i<row+1;i++){
            int j;
            boolean found = false;
            Node tmp = new Node(i,0);
            for(j = 1;j<col+1;j++){
                if(judge[i][j]){
                    if(!found){		// for the first constraint
                        found = true;		//  there has a element in this column, so find it
                        root = tmp;
                        root.Size = j;
                        //cnt[j]++;
                    }
                    else{
                        Node x = new Node(i, j);
                        x.Left = root;
                        root.Right = x;
                        root = x;
                    }
                    root.Col = ColHead[j];
                    ColHead[j].Size++;
                    root.Up = ColTail[j];
                    ColTail[j].Down = root;
                    ColTail[j] = root;
                }
            }
            if(found){		//	there are nothing in the row
                tmp.Left = root;
                root.Right = tmp;
            }
        }
//        com.example.sudoku.Node node = Head.Right.Down;
//        Head.Right = node;
//        node.Left = Head;
        for(int i=1;i<col+1;i++){     //loop
            ColTail[i].Down = ColHead[i];
            ColHead[i].Up = ColTail[i];
        }
    }

    public void TraceBack(int k){
        if(Head.Right == Head){
            answer++;
            res.add((LinkedList<Node>)cur.clone());		// insert the result
            return;
        }
        else {

            // choose column
            int min = Integer.MAX_VALUE;
            int index = -1;
            Node tmp = Head.Right;
            while(tmp != Head){
                if(tmp.Size < min){
                    min = tmp.Size;
                    index = tmp.Name;
                }
                tmp = tmp.Right;
            }
            Node x = ColHead[index];

//        	com.example.sudoku.Node p = Head.Right;
//        	com.example.sudoku.Node tmp = p.Right;
//        	int minNode = cnt[p.Col];
//        	while(tmp != Head) {
//        		if(cnt[tmp.Col] < minNode) {
//        			p = tmp;
//        			minNode = cnt[tmp.Col];
//        		}
//        		tmp = tmp.Right;
//        	}

//        	com.example.sudoku.Node x = p.Down;
            for(int i=0;i<2;i++){
                if(i==0){
                    remove(x);
                    Node y = x.Down;
                    while(y != x) {
                        cur.add(y);
                        Node z = y.Right;
                        while (z != y) {
                            remove(z.Col);
                            z = z.Right;
                        }
                        TraceBack(k + 1);
                        return;
//                        z = y.Left;
//                        while (z != y) {
//                            resume(z.Col);
//                            z = z.Left;
//                        }
//                        cur.removeLast();
//                        y = y.Down;
                    }
//                    resume(x);
                }
                else break;
            }
        }
    }

    private void remove(Node c){
        c.Right.Left = c.Left;
        c.Left.Right = c.Right;
        Node d = c.Down;
        while(d != c){
            Node r = d.Right;
            while(r != d){
                r.Down.Up = r.Up;
                r.Up.Down = r.Down;
                r.Col.Size--;
                r = r.Right;
            }
            d = d.Down;
        }
    }

    private void resume(Node c){
        Node u = c.Up;
        while(u !=c){
            Node l = u.Left;
            while(l != u){
                l.Down.Up = l;
                l.Up.Down = l;
                l.Col.Size++;
                l = l.Left;
            }
            u = u.Up;
        }
        c.Right.Left = c;
        c.Left.Right = c;
    }

    public int getResultCount() {
        return answer;
    }

    public LinkedList<LinkedList<Node>> GetResult() {
        return res;
    }
}

