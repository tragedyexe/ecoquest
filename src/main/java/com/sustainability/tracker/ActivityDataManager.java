package com.sustainability.tracker;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ActivityDataManager {
    private static ActivityDataManager instance;
    private ArrayList<ActivityEntry> activityHistory;
    private int totalScore;
    private ArrayList<DataChangeListener> listeners;
    
    // Singleton pattern to ensure only one data manager exists
    /*Adds listeners so that when new activities are added, the UI can be updated. The history is stored in an array list. */
    private ActivityDataManager() {
        activityHistory = new ArrayList<>();
        totalScore = 850; // Starting score
        listeners = new ArrayList<>();
        
        // Add some sample data
        addSampleData();
    }
    
    public static ActivityDataManager getInstance() {
        if (instance == null) {
            instance = new ActivityDataManager();
        }
        return instance;
    }
    
    // Add a listener to be notified when data changes
    public void addDataChangeListener(DataChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    public void removeDataChangeListener(DataChangeListener listener) {
        listeners.remove(listener);
    }
    
    // Notify all listeners that data has changed
    private void notifyDataChanged() {
        for (DataChangeListener listener : listeners) {
            listener.onDataChanged();
        }
    }
    
    // Add a new activity (called by ActivityLogger)
    public void addActivity(String activityName, int points) {
        ActivityEntry entry = new ActivityEntry(activityName, points, LocalDateTime.now());
        activityHistory.add(0, entry); // Add to beginning
        totalScore += points;
        
        System.out.println("✅ Activity stored: " + activityName + " (+" + points + " points)");
        System.out.println("📊 Total activities: " + activityHistory.size() + " | Total score: " + totalScore);
        
        // Notify all listeners (dashboard) to update
        notifyDataChanged();
    }
    
    // Get all activities
    public ArrayList<ActivityEntry> getAllActivities() {
        return new ArrayList<>(activityHistory);
    }
    
    // Get recent activities (last N)
    public ArrayList<ActivityEntry> getRecentActivities(int count) {
        int size = Math.min(count, activityHistory.size());
        return new ArrayList<>(activityHistory.subList(0, size));
    }
    
    // Get total score
    public int getTotalScore() {
        return totalScore;
    }
    
    // Get activity count
    public int getActivityCount() {
        return activityHistory.size();
    }
    
    // Get total points from all activities
    public int getTotalPoints() {
        int total = 0;
        for (ActivityEntry entry : activityHistory) {
            total += entry.getPoints();
        }
        return total;
    }
    
    // Clear all data (for testing)
    public void clearAllData() {
        activityHistory.clear();
        totalScore = 0;
        notifyDataChanged();
    }
    // Sample data to show when the dashboard is first loaded. 
    /*This can be removed later when real activities are being logged. 
    *It adds a few activities to the history and sets the total score to 930 to give the user a starting point when they first see the dashboard.
    */
    private void addSampleData() {
        activityHistory.add(new ActivityEntry("Started with EcoQuest", 50, LocalDateTime.now().minusDays(2)));
        activityHistory.add(new ActivityEntry("Referred a friend", 30, LocalDateTime.now().minusDays(1)));
        totalScore = 80;
    }
    
    // Inner class for activity entry 
    /*Constructor that adds name, points and timestamp for the activity */
    public static class ActivityEntry {
        private String activityName;
        private int points;
        private LocalDateTime timestamp;
        
        public ActivityEntry(String activityName, int points, LocalDateTime timestamp) {
            this.activityName = activityName;
            this.points = points;
            this.timestamp = timestamp;
        }
        /* Getter methods for activity entry properties */
        public String getActivityName() { return activityName; }
        public int getPoints() { return points; }
        public LocalDateTime getTimestamp() { return timestamp; }
        
        public String getFormattedTimestamp() {
            return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        
        public String getTimeOnly() {
            return timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
    }
    
    // Listener interface for data changes
    public interface DataChangeListener {
        void onDataChanged();
    }
}