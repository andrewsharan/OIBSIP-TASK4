import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class OnlineExamSystem {
    private static final Map<String, String> credentials = new HashMap<>();
    private static final String[] questions = {
            "1. Who is the Father of C? \na)Dennis Ritchie \nb)James Gosling \nc)Guido Van Rossum \nd)Bjarne Stroustrup",
            "2. What is the smallest unit of the information? \na)Byte \nb)Nibble \nc)Bit \nd)Block",
            "3. Which of the following programs enables you to calculate numbers related to rows and columns?" +
                    "\na)Window Program \nb)Spreadsheet Program " +
                    "\nc)Word Program \nd)Graphics Program",
            "4. Which of the following is an output device? \na)Mouse \nb)Light pen \nc)Keyboard \nd)VDU",
            "5. Which of the following is the extension of Notepad? \na).txt \nb).xls \nc).ppt \nd).bmp"
    };

    private static final String[] correctAnswers = {"a","c","b","a","a"};

    private static int score = 0;

    private static User currentUser;
    private static Timer timer;

    public static void main(String[] args) {
        initializeUsers();
        displayWelcomeScreen();

        while (true) {
            int choice = displayMenu();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    updateProfile();
                    break;
                case 3:
                    startExam();
                    break;
                case 4:
                    logout();
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeUsers() {
        credentials.put("Andrew", "user1");
        credentials.put("Javid", "user2");
        credentials.put("Vikram", "user2");
        credentials.put("Rithish", "user2");
        credentials.put("Kaif", "user2");

    }

    private static void displayWelcomeScreen() {
        System.out.println("Online Examination System\n");
    }

    private static int displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Login");
        System.out.println("2. Update Profile and Password");
        System.out.println("3. Start Exam");
        System.out.println("4. Logout");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.next();

        if (credentials.containsKey(username)) {
            System.out.print("Enter your password: ");
            String password = scanner.next();

            if (password.equals(credentials.get(username))) {
                currentUser = new User(username, password);
                System.out.println("Login successful. Welcome, " + username + "!\n");
            }
            else {
                System.out.println("Incorrect password. Login failed.\n");
            }
        }
        else {
            System.out.println("User not found. Login failed.\n");
        }
    }

    private static void updateProfile() {
        if (currentUser != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your new password: ");
            String newPassword = scanner.next();
            credentials.put(currentUser.getUsername(), newPassword);
            System.out.println("Profile and password updated successfully.\n");
        }
        else {
            System.out.println("You must be logged in to update your profile.\n");
        }
    }

    private static void startExam() {
        if (currentUser != null) {
            score = 0;
            System.out.println("Exam started. You have 5 minutes to complete the exam.\n");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    submitExam();
                }
            }, 5 * 60 * 1000);

            Scanner scanner = new Scanner(System.in);
            for (int i = 0; i < questions.length; i++) {
                System.out.println(questions[i]);
                System.out.print("Your answer: ");
                String userAnswer = scanner.next().toLowerCase();

                if (userAnswer.equals(correctAnswers[i])) {
                    System.out.println("Correct!\n");
                    score++;
                }
                else {
                    System.out.println("Incorrect. The correct answer is: " + correctAnswers[i] + "\n");
                }
            }

            submitExam();
        }
        else {
            System.out.println("You must be logged in to start the exam.\n");
        }
    }

    private static void submitExam() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        System.out.println("Exam completed. Your score: " + score + " out of " + questions.length + "\n");
    }

    private static void logout() {
        currentUser = null;
        System.out.println("Logout successful. Goodbye!\n");
    }
}
