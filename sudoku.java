/*
 * easy
 -1  5 -1  2 -1  4  6  1  3
 -1 -1  9 -1  3 -1 -1  4  8
 -1 -1  4 -1  1  5  7 -1 -1
 -1 -1  2 -1  7  6 -1 -1  9
 -1 -1  5 -1 -1 -1  3 -1 -1
  9 -1 -1  4  5 -1  1 -1 -1
 -1 -1  6  7  4 -1  2 -1 -1
  4  3 -1 -1  8 -1  9 -1 -1
  8  2  7  9 -1  1 -1  3 -1
  
 9  7 -1  1 -1 -1 -1 -1  8
-1 -1 -1  5  3  6 -1 -1 -1
 1  3 -1 -1 -1 -1  4  2 -1
 5  9 -1 -1 -1  1  6 -1  2
 6 -1  4 -1 -1 -1  7 -1  3
 8 -1  3  6 -1 -1 -1  4  9
-1  4  8 -1 -1 -1 -1  5  6
-1 -1 -1  8  9  5 -1 -1 -1
 2 -1 -1 -1 -1  7 -1  3  1
  
  *hard
-1 -1  3 -1  9  2 -1 -1 -1
 4 -1 -1 -1  3 -1 -1  1 -1
 2  7 -1 -1 -1 -1 -1 -1 -1
-1  1 -1  3 -1 -1 -1 -1  8
-1  5 -1  1  6  7 -1  3 -1
 3 -1 -1 -1 -1  8 -1  6 -1
-1 -1 -1 -1 -1 -1 -1  5  3
-1  3 -1 -1  8 -1 -1 -1  9
-1 -1 -1  6  2 -1  1 -1 -1

*medium
-1 -1 -1 2 -1 4 8 1 -1
-1 4 -1 -1 -1 8 2 6 3
3 -1 -1 1 6 -1 -1 -1 4
1 -1 -1 -1 4 -1 5 8 -1
6 3 5 8 2 -1 -1 -1 7
2 -1 -1 5 9 -1 1 -1 -1
9 1 -1 7 -1 -1 -1 4 -1
-1 -1 -1 6 8 -1 7 -1 1
8 -1 -1 4 -1 3 -1 5 -1

 */


import java.util.* ;

public class sudoku {
	
	public static void main(String[]args){
		
		Scanner Scan = new Scanner (System.in) ;
		
		boolean[][][] mark_up = new boolean[9][9][10] ;
		
		int[][] sudoku = new int[9][9] ;
		int[][] counter = new int[9][9] ;
		
		//to control if our sudoku solved or not
		int solved_cells_counter = 0 ;
		
		//reading table
		System.out.println("Enter table, (Enter -1 instead of blank cells)") ;
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				sudoku[i][j] = Scan.nextInt() ;
				if(sudoku[i][j]==-1){
					counter[i][j] = 9 ;
				}
				else{
					//solved_cells_counter++ ;
					counter[i][j]=0 ;
				}
			}
		
		first(mark_up, sudoku, counter, solved_cells_counter) ;
		
		//print result
		if(solved_cells_counter==81){
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					System.out.print(sudoku[i][j]+" ") ;
				}
				System.out.println();
			}
			System.out.println("\nend");
		}
		else{
			preemptive_set_finder_2(sudoku,mark_up,counter, solved_cells_counter) ;
			if(solved_cells_counter!=81){
				System.out.println("it's too hard for me !! :(") ;
				for(int i=0;i<9;i++){
					for(int j=0;j<9;j++){
						System.out.print(sudoku[i][j]+" ") ;
					}
					System.out.println();
				}
				System.out.println("\nend");
			}
		}
	}
	
	//............................................................................
	//hazf adad tekrari az list haye satr v sotun v box
	public static void first(boolean[][][] mark_up, int[][] sudoku, int[][] counter, int solved_cells_counter){
		//..
		System.out.println("first called .") ;
		
		for(int i=0; i<9 ; i++){
			for(int j=0 ; j<9 ; j++){
				if(sudoku[i][j]!=-1){
					DoIT(i, j, sudoku, mark_up, counter, solved_cells_counter) ;
				}
			}
		}
	}
		
	//...............................................................................
	// in adade tekrari ra az satro sutun hazf mikonad !!
	public static void DoIT(int p, int q, int[][] sudoku, boolean[][][] mark_up, int[][] counter, int solved_cells_counter){
		System.out.println("do it call with "+p+","+q) ;
		// in row
		for(int j=0;j<9;j++){
			if(j!=q && counter[p][j]!=0){
				if(!mark_up[p][j][sudoku[p][q]]){
					mark_up[p][j][sudoku[p][q]] = true ;
					counter[p][j]-- ;
				}
				
				if(counter[p][j]==1){
					counter[p][j]-- ;
					put(p, j, mark_up, sudoku, solved_cells_counter) ;
					DoIT(p, j, sudoku, mark_up, counter, solved_cells_counter) ;
				}
			}
		}
			
		//in column
		for(int i=0;i<9;i++){
			if(i!=p){
				if(!mark_up[i][q][sudoku[p][q]]){
					mark_up[i][q][sudoku[p][q]] = true ;
					counter[i][q]-- ;
				}
				if(counter[i][q]==1){
					counter[i][q]-- ;
					put(i, q, mark_up, sudoku, solved_cells_counter) ;
					DoIT(i, q, sudoku, mark_up, counter, solved_cells_counter) ;
				}
			}
		}
			
		// in 3*3 square
		int r = 3*(p/3), c = 3*(q/3) ;
		for(int i=r ; i<r+3 ; i++){
			for(int j=c ; j<c+3 ; j++){
				if(!mark_up[i][j][sudoku[p][q]]){
					mark_up[i][j][sudoku[p][q]] = true ;
					counter[i][j]-- ;
				}
				if(counter[i][j]==1){
					counter[i][j]-- ;
					put(i, j, mark_up, sudoku, solved_cells_counter) ;
					DoIT(i, j, sudoku, mark_up, counter, solved_cells_counter) ;
				}
			}
		}
	}
		
	//...........................................................
	public static void put(int i, int j, boolean[][][] mark_up, int[][] sudoku, int solved_cells_counter){
		System.out.println("put call with "+i+","+j) ;
		if(sudoku[i][j]!=-1)
			return ;
		for(int k=1 ; k<10 ; k++){
			if(!mark_up[i][j][k]){
				solved_cells_counter++ ;
				System.out.println("i put "+k+" in the table "+i+","+j) ;
				sudoku[i][j] = k ;
				break ;
			}
		}
	}
	//.....................................................................
	public static void preemptive_set_finder_2 (int[][] sudoku, boolean[][][] mark_up, int[][] counter, int solved_cells_counter){
		System.out.println("we are injaaa ************ :D") ;
		//in rows
		for(int i=0 ; i<9 ; i++){
			// i ruye satr ha harkat mikonad
			for(int j=0 ; j<9 ; j++){
				for(int k=j+1 ; k<9 ;k++){
					if(counter[i][j]==2 && counter[i][k]==2){
						check_row(i, j, k, sudoku, mark_up, counter, solved_cells_counter) ;
					}
				}
			}
		}
		//in culomns
		for(int i=0 ; i<9 ; i++){
			// i ruye satr ha harkat mikonad
			for(int j=0 ; j<9 ; j++){
				for(int k=j+1 ; k<9 ;k++){
					if(counter[j][i]==2 && counter[k][i]==2){
						check_column(i, j, k, sudoku, mark_up, counter, solved_cells_counter) ;
					}
				}
			}
		}
		for(int i=0 ; i<9 ; i+=3){
			for(int j=0 ; j<9 ; j+=3){
				for(int p=i ; p<3 ; p++){
					for(int q=j ; q<3 ; q++){
						for(int m=p+1 ; m<3 ; m++){
							for(int n=q+1 ; q<3 ; q++){
								if(counter[m][n]==2 && counter[p][q]==2){
									check_box(i,j,i+p,j+q,i+m,j+n,sudoku, mark_up, counter, solved_cells_counter) ;
								}
							}
							
						}
					}
				}
			}
		}
	}
	//..............................................................................................
	//.... do p...... yani age to y satri do ta khune ba adade yeksan dashtan, un 2ta tu baghie nemitunan bashan
	public static void check_row(int row, int p, int q, int[][] sudoku, boolean[][][] mark_up, int[][] counter, int solved_cells_counter){
		LinkedList<Integer> same = new LinkedList<>() ;
		for(int i=1 ; i<10 ; i++){
			if(mark_up[row][p][i]!=mark_up[row][q][i]){
				return ;
			}
			if(mark_up[row][p][i]==false){
				same.add(i) ;
			}
		}
		while(!same.isEmpty()){
			for(int j=0 ; j<9 ; j++){
				if(j!=p && j!=q && counter[row][j]!=0){
					int temp = same.removeLast() ;
					if(!mark_up[row][j][temp]){
						mark_up[row][j][temp] = true ;
						counter[row][j]-- ;
					}
					if(counter[row][j]==1){
						counter[row][j]-- ;
						put(row, j, mark_up, sudoku, solved_cells_counter) ;
						DoIT(row, j, sudoku, mark_up, counter, solved_cells_counter) ;
					}
				}
			}
		}
	
	}
	//.....................................................................
	public static void check_column(int column, int p, int q, int[][] sudoku, boolean[][][] mark_up, int[][] counter, int solved_cells_counter){
		LinkedList<Integer> same = new LinkedList<>() ;
		for(int i=1 ; i<10 ; i++){
			if(mark_up[p][column][i]!=mark_up[q][column][i]){
				return ;
			}
			if(mark_up[p][column][i]==false){
				same.add(i) ;
			}
		}
		while(!same.isEmpty()){
			int temp = same.removeLast() ;
			for(int j=0 ; j<9 ; j++){
				if(j!=p && j!=q && counter[j][column]!=0)
					if(!mark_up[j][column][temp]){
						mark_up[j][column][temp] = true ;
						counter[j][column]-- ;
					}
					if(counter[j][column]==1){
						counter[j][column]-- ;
						put(j, column, mark_up, sudoku, solved_cells_counter) ;
						DoIT(j, column, sudoku, mark_up, counter, solved_cells_counter) ;
					}
				}
			}
		}
	
	//.........................................................................
	public static void check_box(int x, int y, int a, int b, int p, int q, int[][] sudoku, boolean[][][] mark_up, int[][] counter, int solved_cells_counter){
		LinkedList<Integer> same = new LinkedList<>() ;
		for(int i=1 ; i<10 ; i++){
			if(mark_up[a][b][i]!=mark_up[p][q][i]){
				return ;
			}
			if(mark_up[p][q][i]==false){
				same.add(i) ;
			}
		}
		while(!same.isEmpty()){
			for(int i=x ; i<x+3 ; i++){
				for(int j=y ; j<y+3 ; j++){
					if(((i!=a)&&(j!=b))||((i!=p)&&(j!=q))){
						int temp = same.removeLast() ;
						if(!mark_up[i][j][temp]){
							mark_up[i][j][temp] = true ;
							counter[i][j]-- ;
						}
						if(counter[i][j]==1){
							counter[i][j]-- ;
							put(i, j, mark_up, sudoku, solved_cells_counter) ;
							DoIT(i, j, sudoku, mark_up, counter, solved_cells_counter) ;
						}
					}
				}
			}
		}
	}
}
