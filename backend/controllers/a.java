   for (int k = 0; k < R; k++) 
             { 
                 System.out.print(arr[k][0]); 
                   
                 int i = k - 1;   
                                  
                 int j = 1;      
                                 
            
                
                 while (isValid(i, j,R,C)) 
                 { 
                     System.out.print(arr[i][j]); 
                       
                     i--; 
                     j++;
                 } 
                   
                 System.out.println(""); 
             } 
            
        
             for (int k = 1; k < C; k++) 
             { 
                 System.out.print(arr[R-1][k]); 
                   
                 int i = R - 2; 
                               
                 int j = k + 1; 
                 while (isValid(i, j,R,C)) 
                 { 
                     System.out.print(arr[i][j]);     
                     i--; 
                     j++; 
                 } 
                   
                 System.out.println(""); 
             } 


  public boolean isValid(int i, int j ,int R ,int C) 
     { 
         if (i < 0 || i >= R || j >= C || j < 0) return false; 
         return true; 
     } 