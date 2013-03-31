package aurora.V1.core;


import java.io.File;
import java.util.ArrayList;

public final class Finder {

    public static String[] GameProviders = {"Ubisoft"}; //add your own

    /**
     * PRECONDITION: You are on a Windows PC, Java source 7 to compile
     *
     * @return ArrayList of File objects representing Games on hard drive from
     *         these game distributors Steam, and Origin. (game creators are in String
     *         array "GameProviders")
     *
     */
    public static ArrayList<File> getExecutablePathsOnCDrive() {
        ArrayList<File> r = new ArrayList<File>();
        { //Steam
            {
                File gameFolder = new File(
                        "C:\\Program Files (x86)\\Steam\\SteamApps\\common");
                if (gameFolder.exists() && gameFolder.isDirectory()) {
                    File[] games = gameFolder.listFiles();
                    for (File game : games) {
                        ArrayList<File> possibles = getAllFilesInSubDirectories(
                                game, "exe");
                        r.add(getLargestFile(possibles));
                    }
                }
            }
            {
                File gameFolder = new File(
                        "C:\\Program Files\\Steam\\SteamApps\\common");
                if (gameFolder.exists() && gameFolder.isDirectory()) {
                    File[] games = gameFolder.listFiles();
                    for (File game : games) {
                        ArrayList<File> possibles = getAllFilesInSubDirectories(
                                game, "exe");
                        r.add(getLargestFile(possibles));
                    }
                }
            }
        }

        { //Origin
            {
                File gameFolder = new File(
                        "C:\\Program Files (x86)\\Origin Games");
                if (gameFolder.exists() && gameFolder.isDirectory()) {
                    File[] games = gameFolder.listFiles();
                    for (File game : games) {
                        ArrayList<File> possibles = getAllFilesInSubDirectories(
                                game, "exe");
                        r.add(getLargestFile(possibles));
                    }
                }
            }
            {
                File gameFolder = new File("C:\\Program Files\\Origin Games");
                if (gameFolder.exists() && gameFolder.isDirectory()) {
                    File[] games = gameFolder.listFiles();
                    for (File game : games) {
                        ArrayList<File> possibles = getAllFilesInSubDirectories(
                                game, "exe");
                        r.add(getLargestFile(possibles));
                    }
                }
            }
        }

        { //General Game Providers
            for (String providers : GameProviders) {
                {
                    File gameFolder = new File("C:\\Program Files (x86)\\"
                                               + providers);
                    if (gameFolder.exists() && gameFolder.isDirectory()) {
                        File[] games = gameFolder.listFiles();
                        for (File game : games) {
                            ArrayList<File> possibles = getAllFilesInSubDirectories(
                                    game, "exe");
                            r.add(getLargestFile(possibles));
                        }
                    }
                }
                {
                    File gameFolder = new File("C:\\Program Files\\" + providers);
                    if (gameFolder.exists() && gameFolder.isDirectory()) {
                        File[] games = gameFolder.listFiles();
                        for (File game : games) {
                            ArrayList<File> possibles = getAllFilesInSubDirectories(
                                    game, "exe");
                            r.add(getLargestFile(possibles));
                        }
                    }
                }
            }
        }

        return r;
    }

    /**
     * PRECONDITION: You are on a Windows PC, Java source 7 to compile
     *
     * @return ArrayList of String objects representing the name of Games on
     *         these game distributors Steam, and Origin. (game creators are in String
     *         array "GameProviders")
     */
    public static ArrayList<String> getNameOfGamesOnCDrive() {
        ArrayList<String> r = new ArrayList<String>();
        { //Steam
            {
                File gameFolder = new File(
                        "C:\\Program Files (x86)\\Steam\\SteamApps\\common");
                if (gameFolder.exists() && gameFolder.isDirectory()) {
                    File[] games = gameFolder.listFiles();
                    for (File game : games) {
                        r.add(game.getName());
                    }
                }
            }
            {
                File gameFolder = new File(
                        "C:\\Program Files\\Steam\\SteamApps\\common");
                if (gameFolder.exists() && gameFolder.isDirectory()) {
                    File[] games = gameFolder.listFiles();
                    for (File game : games) {
                        r.add(game.getName());
                    }
                }
            }
        }

        { //Origin
            {
                File gameFolder = new File(
                        "C:\\Program Files (x86)\\Origin Games");
                if (gameFolder.exists() && gameFolder.isDirectory()) {
                    File[] games = gameFolder.listFiles();
                    for (File game : games) {
                        r.add(game.getName());
                    }
                }
            }
            {
                File gameFolder = new File("C:\\Program Files\\Origin Games");
                if (gameFolder.exists() && gameFolder.isDirectory()) {
                    File[] games = gameFolder.listFiles();
                    for (File game : games) {
                        r.add(game.getName());
                    }
                }
            }
        }

        { //General Game Providers
            for (String providers : GameProviders) {
                {
                    File gameFolder = new File("C:\\Program Files (x86)\\"
                                               + providers);
                    if (gameFolder.exists() && gameFolder.isDirectory()) {
                        File[] games = gameFolder.listFiles();
                        for (File game : games) {
                            r.add(game.getName());
                        }
                    }
                }
                {
                    File gameFolder = new File("C:\\Program Files\\" + providers);
                    if (gameFolder.exists() && gameFolder.isDirectory()) {
                        File[] games = gameFolder.listFiles();
                        for (File game : games) {
                            r.add(game.getName());
                        }
                    }
                }
            }
        }

        return r;
    }

    /**
     * PRECONDITION: You are on a Windows PC, Java source 7 to compile, and
     * mainDir is not null
     *
     * @param mainDir       A directory/file on the hard drive
     * @param extensionOnly Grabs files with a certain extension only
     *
     * @return ArrayList of File objects representing everyfile in the directory
     *         denotated by mainDir and it's sub directories And only finds ones with
     *         the extension specified in the parameter extensionOnly
     *
     * @exception StackOverflowError When the search becomes too large (This is
     *                               a recursive search)
     */
    public static ArrayList<File> getAllFilesInSubDirectories(File mainDir,
                                                              String extensionOnly) {
        ArrayList<File> r = new ArrayList<File>();
        if (!mainDir.isDirectory()) {
            try {
                if (mainDir.getAbsolutePath().substring(mainDir
                        .getAbsolutePath().lastIndexOf(".") + 1).compareTo(
                        extensionOnly) == 0) {
                    r.add(mainDir);
                }
            } catch (StringIndexOutOfBoundsException e) {
            }
        } else {
            File[] ls = mainDir.listFiles();
            if (ls != null) {
                for (File f : ls) {
                    if (f != null) {
                        if (!f.isDirectory()) {
                            try {
                                if (f.getAbsolutePath().substring(f
                                        .getAbsolutePath().lastIndexOf(".") + 1)
                                        .compareTo(extensionOnly) == 0) {
                                    r.add(f);
                                }
                            } catch (StringIndexOutOfBoundsException e) {
                            }
                        } else {
                            for (File ff : getAllFilesInSubDirectories(f)) {
                                try {
                                    if (ff.getAbsolutePath().substring(ff
                                            .getAbsolutePath().lastIndexOf(".")
                                                                       + 1)
                                            .compareTo(extensionOnly) == 0) {
                                        r.add(ff);
                                    }
                                } catch (StringIndexOutOfBoundsException e) {
                                }
                            }
                        }
                    }
                }
            }
        }
        return r;
    }

    /**
     * PRECONDITION: You are on a Windows PC, Java source 7 to compile, and
     * mainDir is not null
     *
     * @param mainDir A directory/file on the hard drive
     *
     * @return ArrayList of File objects representing every file in directory
     *         denotated by mainDir and it's sub directories
     *
     * @exception StackOverflowError When the search becomes too large (This is
     *                               a recursive search)
     */
    public static ArrayList<File> getAllFilesInSubDirectories(File mainDir) {
        ArrayList<File> r = new ArrayList<File>();
        if (!mainDir.isDirectory()) {
            r.add(mainDir);
        } else {
            File[] ls = mainDir.listFiles();
            if (ls != null) {
                for (File f : ls) {
                    if (f != null) {
                        if (!f.isDirectory()) {
                            r.add(f);
                        } else {
                            for (File ff : getAllFilesInSubDirectories(f)) {
                                r.add(ff);
                            }
                        }
                    }
                }
            }
        }
        return r;
    }

    /**
     * PRECONDITION: possibles is not null, and has atleast one element
     *
     * @param possibles an Array of File objects
     *
     * @return File object that represents largest file in the Array denoted by
     *         the variable possibles
     */
    public static File getLargestFile(ArrayList<File> possibles) {
        File r = possibles.get(0);
        for (int i = 1; i < possibles.size(); i++) {
            File compare = possibles.get(i);
            if (r.length() < compare.length()) {
                r = compare;
            }
        }
        return r;
    }
}