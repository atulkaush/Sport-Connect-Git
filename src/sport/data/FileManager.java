package sport.data;

import sport.model.AthleteProfile;
import sport.model.CoachProfile;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_NAME = "profiles.txt";

    private String clean(String text) {
        if (text == null) {
            return "";
        }

        return text.replace("|", "/")
                   .replace("\n", " ")
                   .replace("\r", " ");
    }

    // Save an athlete profile
    public void saveAthleteProfile(AthleteProfile profile) {
        String record = "ATHLETE|"
                + clean(profile.getUsername()) + "|"
                + clean(profile.getSport()) + "|"
                + clean(profile.getPosition()) + "|"
                + clean(profile.getAchievements()) + "|"
                + clean(profile.getLocation());

        saveRecord(profile.getUsername(), record);
    }

    // Save a coach profile
    public void saveCoachProfile(CoachProfile profile) {
        String record = "COACH|"
                + clean(profile.getUsername()) + "|"
                + clean(profile.getSpecialization()) + "|"
                + profile.getExperience() + "|"
                + clean(profile.getLocation()) + "|"
                + clean(profile.getBio());

        saveRecord(profile.getUsername(), record);
    }

    // Add a new profile or replace the existing profile for that username
    private void saveRecord(String username, String newRecord) {
        ArrayList<String> records = new ArrayList<>();
        boolean replaced = false;

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);

                if (parts.length >= 2
                        && parts[1].equalsIgnoreCase(username)) {

                    if (!replaced) {
                        records.add(newRecord);
                        replaced = true;
                    }
                } else {
                    records.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            // The file will be created when writing.
        } catch (IOException e) {
            System.out.println("Error reading profiles: " + e.getMessage());
            return;
        }

        if (!replaced) {
            records.add(newRecord);
        }

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (String record : records) {
                writer.write(record);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving profile: " + e.getMessage());
        }
    }

    // Load all athlete profiles
    public ArrayList<AthleteProfile> loadAthleteProfiles() {
        ArrayList<AthleteProfile> athletes = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);

                if (parts.length == 6 && parts[0].equals("ATHLETE")) {
                    AthleteProfile profile = new AthleteProfile(
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            parts[5]
                    );

                    athletes.add(profile);
                }
            }

        } catch (FileNotFoundException e) {
            // No profiles have been saved yet.
        } catch (IOException e) {
            System.out.println("Error loading athletes: " + e.getMessage());
        }

        return athletes;
    }

    // Load all coach profiles
    public ArrayList<CoachProfile> loadCoachProfiles() {
        ArrayList<CoachProfile> coaches = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);

                if (parts.length == 6 && parts[0].equals("COACH")) {
                    try {
                        int experience = Integer.parseInt(parts[3]);

                        CoachProfile profile = new CoachProfile(
                                parts[1],
                                parts[2],
                                experience,
                                parts[4],
                                parts[5]
                        );

                        coaches.add(profile);

                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid coach record.");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            // No profiles have been saved yet.
        } catch (IOException e) {
            System.out.println("Error loading coaches: " + e.getMessage());
        }

        return coaches;
    }
}