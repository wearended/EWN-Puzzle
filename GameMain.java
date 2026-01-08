public class GameMain {

    public static void main(String[] args) {
       
        Scanner input = new Scanner(System.in);
        
        System.out.println("=== SIMPLE EWN PUZZLE GAME ===");
        System.out.println("Welcome to the puzzle game!");
        
        // Ask for player type
        System.out.println("\nChoose your player type:");
        System.out.println("1. I want to play myself (Human)");
        System.out.println("2. Let computer play randomly");
        System.out.print("Enter 1 or 2: ");
        
        int choice = input.nextInt();
        
        // Create the player
        SimplePlayer player;
        if (choice == 1) {
            player = new HumanPlayer();
            System.out.println("You chose: Human Player");
        } else {
            player = new RandomPlayer();
            System.out.println("You chose: Random Computer Player");
        }
        
        // Ask for level file
        System.out.print("\nEnter level file name (e.g., level1.txt): ");
        String fileName = input.next();
        
        // Start the game
        startGame(fileName, player);
        
        input.close();
    }
    }
}

