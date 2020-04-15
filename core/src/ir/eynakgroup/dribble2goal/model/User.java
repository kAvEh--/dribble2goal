package ir.eynakgroup.dribble2goal.model;

public class User {
    private String name = "---";
    private String Id = "";
    private int position = 1;
    private int position_num = 3;
    private int shirt = 1;
    private int avatar = 1;
    private int coins_num = 500;
    private int level = 1;
    private int xp = 105;
    private int game_played = 0;
    private int game_won = 0;
    private int goals = 0;
    private int winInaRaw = 0;
    private boolean isLevelUp = false;
    private int win_percent = 100;
    private int last5 = 100;
    private int achieve_goal = 0;
    private int achieve_cleanSheet = 0;
    private int achieve_win = 0;
    private int achieve_winInaRow = 0;
    private int winRate = 0;
    private int cleanSheet = 0;
    public int[][] players = new int[5][3];
    public int[] lineup = new int[5];
    public int[] shirts = new int[24];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition_num() {
        return position_num;
    }

    public void setPosition_num(int position_num) {
        this.position_num = position_num;
    }

    public int getShirt() {
        return shirt;
    }

    public void setShirt(int shirt) {
        this.shirt = shirt;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getCoins_num() {
        return coins_num;
    }

    public void setCoins_num(int coins_num) {
        this.coins_num = coins_num;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getGame_played() {
        return game_played;
    }

    public void setGame_played(int game_played) {
        this.game_played = game_played;
    }

    public int getGame_won() {
        return game_won;
    }

    public void setGame_won(int game_won) {
        this.game_won = game_won;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getWinInaRaw() {
        return winInaRaw;
    }

    public void setWinInaRaw(int winInaRaw) {
        this.winInaRaw = winInaRaw;
    }

    public boolean isLevelUp() {
        return isLevelUp;
    }

    public void setLevelUp(boolean levelUp) {
        isLevelUp = levelUp;
    }

    public int getWin_percent() {
        return win_percent;
    }

    public void setWin_percent(int win_percent) {
        this.win_percent = win_percent;
    }

    public int getLast5() {
        return last5;
    }

    public void setLast5(int last5) {
        this.last5 = last5;
    }

    public int getAchieve_cleanSheet() {
        return achieve_cleanSheet;
    }

    public void setAchieve_cleanSheet(int achieve_cleanSheet) {
        this.achieve_cleanSheet = achieve_cleanSheet;
    }

    public int getAchieve_win() {
        return achieve_win;
    }

    public void setAchieve_win(int achieve_win) {
        this.achieve_win = achieve_win;
    }

    public int getAchieve_winInaRow() {
        return achieve_winInaRow;
    }

    public void setAchieve_winInaRow(int achieve_winInaRow) {
        this.achieve_winInaRow = achieve_winInaRow;
    }

    public int getWinRate() {
        return winRate;
    }

    public void setWinRate(int winRate) {
        this.winRate = winRate;
    }

    public int getCleanSheet() {
        return cleanSheet;
    }

    public void setCleanSheet(int cleanSheet) {
        this.cleanSheet = cleanSheet;
    }

    public int getAchieve_goal() {
        return achieve_goal;
    }

    public void setAchieve_goal(int achieve_goal) {
        this.achieve_goal = achieve_goal;
    }
}
