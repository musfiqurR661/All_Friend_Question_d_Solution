import java.io.*;
import java.util.*;

class Candidate {
    private String name;
    private int votes;

    public Candidate(String candidateName) {
        name = candidateName;
        votes = 0;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    public void incrementVotes() {
        votes++;
    }
}

class VoteManagementSystem {
    private List<Candidate> candidates;
    private String fileName;

    public VoteManagementSystem(String filename) {
        fileName = filename;
        candidates = new ArrayList<>();
        loadCandidates();
    }

    public void addCandidate(String candidateName) {
        candidates.add(new Candidate(candidateName));
    }

    public void voteForCandidate(String candidateName) {
        for (Candidate candidate : candidates) {
            if (candidate.getName().equals(candidateName)) {
                candidate.incrementVotes();
                return;
            }
        }
        throw new IllegalArgumentException("Invalid candidate name");
    }

    public void displayCandidates() {
        for (Candidate candidate : candidates) {
            System.out.println(candidate.getName() + ": " + candidate.getVotes() + " votes");
        }
    }

    private void loadCandidates() {
        try (Scanner inFile = new Scanner(new File(fileName))) {
            while (inFile.hasNext()) {
                String candidateName = inFile.next();
                candidates.add(new Candidate(candidateName));
            }
        } catch (FileNotFoundException e) {
            // File not found, ignore for now
        }
    }

    private void saveCandidates() {
        try (PrintWriter outFile = new PrintWriter(new FileWriter(fileName))) {
            for (Candidate candidate : candidates) {
                outFile.println(candidate.getName() + " " + candidate.getVotes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        saveCandidates();
    }
}

public class FahimProject{
    public static void main(String[] args) {
        VoteManagementSystem voteSystem = new VoteManagementSystem("candidates.txt");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Candidate");
            System.out.println("2. Vote for Candidate");
            System.out.println("3. Display Candidates and Votes");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: {
                    System.out.print("Enter candidate name: ");
                    String candidateName = scanner.nextLine();
                    voteSystem.addCandidate(candidateName);
                    break;
                }
                case 2: {
                    System.out.print("Enter candidate name: ");
                    String candidateName = scanner.nextLine();
                    try {
                        voteSystem.voteForCandidate(candidateName);
                        System.out.println("Vote recorded.");
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 3:
                    voteSystem.displayCandidates();
                    break;
                case 4:
                    voteSystem.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
