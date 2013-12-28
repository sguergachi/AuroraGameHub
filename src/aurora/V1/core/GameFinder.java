/*
 * Copyright 2012 Sardonix Creative.
 *
 * This work is licensed under the
 * Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/
 *
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aurora.V1.core;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.prefs.Preferences;

public final class GameFinder {

    public static String[] GameProviders = {"Ubisoft", "EA Games", "GOG.com",
        "SQUARE ENIX", "GOG Games"}; //add your own

    /**
     * PRECONDITION: You are on a Windows PC, Java source 6 to compile
     *
     * @return returns the game directory of Steam on the hard drive and if not
     *         found null
     *
     */
    public static File fetchSteamDir() {
        File steamFile = null;
        final int KEY_READ = 0x20019;
        final Preferences userRoot = Preferences.userRoot();
        final Preferences systemRoot = Preferences.systemRoot();
        final Class clz = userRoot.getClass();
        try {
            final Method openKey = clz.getDeclaredMethod("openKey",
                    byte[].class, int.class, int.class);
            openKey.setAccessible(true);
            final Method closeKey = clz
                    .getDeclaredMethod("closeKey",
                            int.class);
            closeKey.setAccessible(true);
            final Method winRegQueryValue = clz.getDeclaredMethod(
                    "WindowsRegQueryValueEx", int.class,
                    byte[].class);
            winRegQueryValue.setAccessible(true);
            final Method winRegEnumValue = clz.getDeclaredMethod(
                    "WindowsRegEnumValue1", int.class, int.class,
                    int.class);
            winRegEnumValue.setAccessible(true);
            final Method winRegQueryInfo = clz.getDeclaredMethod(
                    "WindowsRegQueryInfoKey1", int.class);
            winRegQueryInfo.setAccessible(true);
            byte[] valb;
            String vals;
            String key;
            Integer handle;
            key = "Software\\Classes\\steam\\Shell\\Open\\Command";
            handle = (Integer) openKey.invoke(systemRoot,
                    toCstr(key),
                    KEY_READ, KEY_READ);
            valb = (byte[]) winRegQueryValue.invoke(systemRoot,
                    handle,
                    toCstr(""));
            vals = (valb != null ? new String(valb).trim() : null);
            closeKey.invoke(Preferences.systemRoot(), handle);
            int steamExeIndex = vals.indexOf("steam.exe");
            if (steamExeIndex > 0) {
                String steamPath = vals.substring(1, steamExeIndex);
                steamPath = steamPath + "\\steamapps\\common";
                steamFile = new File(steamPath);
            }
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return steamFile;
    }

    /**
     * PRECONDITION: You are on a Windows PC, Java source 6 to compile
     *
     * @return returns the game directory of Origin on the hard drive and if not
     *         found null
     *
     */
    public static File fetchOriginDir() {
        File originFile = null;
        final int KEY_READ = 0x20019;
        final Preferences userRoot = Preferences.userRoot();
        final Preferences systemRoot = Preferences.systemRoot();
        final Class clz = userRoot.getClass();
        try {
            final Method openKey = clz.getDeclaredMethod("openKey",
                    byte[].class, int.class, int.class);
            openKey.setAccessible(true);
            final Method closeKey = clz
                    .getDeclaredMethod("closeKey",
                            int.class);
            closeKey.setAccessible(true);
            final Method winRegQueryValue = clz.getDeclaredMethod(
                    "WindowsRegQueryValueEx", int.class,
                    byte[].class);
            winRegQueryValue.setAccessible(true);
            final Method winRegEnumValue = clz.getDeclaredMethod(
                    "WindowsRegEnumValue1", int.class, int.class,
                    int.class);
            winRegEnumValue.setAccessible(true);
            final Method winRegQueryInfo = clz.getDeclaredMethod(
                    "WindowsRegQueryInfoKey1", int.class);
            winRegQueryInfo.setAccessible(true);
            byte[] valb;
            String vals;
            String key;
            Integer handle;
            key = "Software\\Classes\\origin\\Shell\\Open\\Command";
            handle = (Integer) openKey.invoke(systemRoot,
                    toCstr(key),
                    KEY_READ, KEY_READ);
            valb = (byte[]) winRegQueryValue.invoke(systemRoot,
                    handle,
                    toCstr(""));
            vals = (valb != null ? new String(valb).trim() : null);
            closeKey.invoke(Preferences.systemRoot(), handle);
            int originExeIndex = vals.indexOf("\\Origin\\Origin.exe");
            if (originExeIndex > 0) {
                String originPath = vals.substring(1, originExeIndex);
                originPath = originPath + "\\Origin Games";
                originFile = new File(originPath);
            }
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return originFile;
    }

    private static byte[] toCstr(String str) {
        byte[] result = new byte[str.length() + 1];
        for (int i = 0; i < str.length(); i++) {
            result[i] = (byte) str.charAt(i);
        }
        result[str.length()] = 0;
        return result;
    }

    /**
     * PRECONDITION: You are on a Windows PC, Java source 6 to compile
     *
     * @param gameNames You must use "getNameOfGamesOnDrive()" for this
     *                  parameter
     *
     * @return ArrayList of File objects representing Games on hard drive from
     *         these game distributors Steam, and Origin. (game creators are in String
     *         array "GameProviders")
     *
     */
    public static ArrayList<File> getExecutablePathsOnDrive(
            ArrayList<String> gameNames) {
        ArrayList<File> r = new ArrayList<File>();
        { //Steam
            File gameFolder = fetchSteamDir();
            if (gameFolder != null && gameFolder.exists() && gameFolder
                    .isDirectory()) {
                File[] games = gameFolder.listFiles();
                for (File game : games) {
                    String n = game.getName();
                    int index1 = n.indexOf("launcher"), index2 = n.indexOf(
                            "Launcher");
                    if (index1 < 0 && index2 < 0) {
                        ArrayList<Files> possibles = getAllFilesInSubDirectories(
                                game, "exe");
                        Files f = getSimilarNamedFile(possibles, gameNames.get(r
                                .size()));
                        if (f != null) {
                            r.add(f);
                        } else {
                            ArrayList<Files> arr = getSmallestDepth(possibles);
                            if (arr.size() == 1) {
                                r.add(arr.get(0));
                            } else {
                                r.add(getLargestFile(arr));
                            }
                        }
                    }
                }
            }
        }
        { //Origin
            File gameFolder = fetchOriginDir();
            if (gameFolder != null && gameFolder.exists() && gameFolder
                    .isDirectory()) {
                File[] games = gameFolder.listFiles();
                for (File game : games) {
                    String n = game.getName();
                    int index1 = n.indexOf("launcher"), index2 = n.indexOf(
                            "Launcher");
                    if (index1 < 0 && index2 < 0) {
                        ArrayList<Files> possibles = getAllFilesInSubDirectories(
                                game, "exe");
                        Files f = getSimilarNamedFile(possibles, gameNames.get(r
                                .size()));
                        if (f != null) {
                            r.add(f);
                        } else {
                            ArrayList<Files> arr = getSmallestDepth(possibles);
                            if (arr.size() == 1) {
                                r.add(arr.get(0));
                            } else {
                                r.add(getLargestFile(arr));
                            }
                        }
                    }
                }
            }
        }
        { //Gamefly
            File gameFolderTop = new File(System.getenv("USERPROFILE")
                                          + "\\My Documents\\GameFly\\games\\");
            if (gameFolderTop.exists() && gameFolderTop.isDirectory()) {
                File[] gameFolders = gameFolderTop.listFiles();
                for (File gameFolder : gameFolders) {
                    File[] games = gameFolder.listFiles();
                    for (File game : games) {
                        String n = game.getName();
                        int index1 = n.indexOf("launcher"), index2 = n.indexOf(
                                "Launcher");
                        if (index1 < 0 && index2 < 0) {
                            ArrayList<Files> possibles = getAllFilesInSubDirectories(
                                    game, "exe");
                            Files f = getSimilarNamedFile(possibles, gameNames
                                    .get(r.size()));
                            if (f != null) {
                                r.add(f);
                            } else {
                                ArrayList<Files> arr = getSmallestDepth(
                                        possibles);
                                if (arr.size() == 1) {
                                    r.add(arr.get(0));
                                } else {
                                    r.add(getLargestFile(arr));
                                }
                            }
                        }
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
                            String n = game.getName();
                            int index1 = n.indexOf("launcher"), index2 = n
                                    .indexOf("Launcher");
                            if (index1 < 0 && index2 < 0) {
                                ArrayList<Files> possibles = getAllFilesInSubDirectories(
                                        game, "exe");
                                Files f = getSimilarNamedFile(possibles,
                                        gameNames.get(r.size()));
                                if (f != null) {
                                    r.add(f);
                                } else {
                                    ArrayList<Files> arr = getSmallestDepth(
                                            possibles);
                                    if (arr.size() == 1) {
                                        r.add(arr.get(0));
                                    } else {
                                        r.add(getLargestFile(arr));
                                    }
                                }
                            }
                        }
                    }

                }

                {
                    //Check in C drive
                    File gameFolder = new File("C:\\" + providers);
                    if (gameFolder.exists() && gameFolder.isDirectory()) {
                        File[] games = gameFolder.listFiles();
                        for (File game : games) {
                            String n = game.getName();
                            int index1 = n.indexOf("launcher"), index2 = n
                                    .indexOf("Launcher");
                            if (index1 < 0 && index2 < 0) {
                                ArrayList<Files> possibles = getAllFilesInSubDirectories(
                                        game, "exe");
                                Files f = getSimilarNamedFile(possibles,
                                        gameNames.get(r.size()));
                                if (f != null) {
                                    r.add(f);
                                } else {
                                    ArrayList<Files> arr = getSmallestDepth(
                                            possibles);
                                    if (arr.size() == 1) {
                                        r.add(arr.get(0));
                                    } else {
                                        r.add(getLargestFile(arr));
                                    }
                                }
                            }
                        }
                    }

                }

                {

                    File gameFolder = new File("C:\\Program Files\\" + providers);
                    if (gameFolder.exists() && gameFolder.isDirectory()) {
                        File[] games = gameFolder.listFiles();
                        for (File game : games) {
                            String n = game.getName();
                            int index1 = n.indexOf("launcher"), index2 = n
                                    .indexOf("Launcher");
                            if (index1 < 0 && index2 < 0) {
                                ArrayList<Files> possibles = getAllFilesInSubDirectories(
                                        game, "exe");
                                Files f = getSimilarNamedFile(possibles,
                                        gameNames.get(r.size()));
                                if (f != null) {
                                    r.add(f);
                                } else {
                                    ArrayList<Files> arr = getSmallestDepth(
                                            possibles);
                                    if (arr.size() == 1) {
                                        r.add(arr.get(0));
                                    } else {
                                        r.add(getLargestFile(arr));
                                    }
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
     * PRECONDITION: You are on a Windows PC, Java source 6 to compile
     *
     * @return ArrayList of String objects representing the name of Games on
     *         these game distributors Steam, and Origin. (game creators are in String
     *         array "GameProviders")
     */
    public static ArrayList<String> getNameOfGamesOnDrive() {
        ArrayList<String> r = new ArrayList<String>();
        { //Steam
            File gameFolder = fetchSteamDir();
            if (gameFolder != null && gameFolder.exists() && gameFolder
                    .isDirectory()) {
                File[] games = gameFolder.listFiles();
                for (File game : games) {
                    String n = game.getName();
                    int index1 = n.indexOf("launcher"), index2 = n.indexOf(
                            "Launcher");
                    if (index1 < 0 && index2 < 0) {
                        r.add(n);
                    }
                }
            }
        }
        { //Origin
            File gameFolder = fetchOriginDir();
            if (gameFolder != null && gameFolder.exists() && gameFolder
                    .isDirectory()) {
                File[] games = gameFolder.listFiles();
                for (File game : games) {
                    String n = game.getName();
                    int index1 = n.indexOf("launcher"), index2 = n.indexOf(
                            "Launcher");
                    if (index1 < 0 && index2 < 0) {
                        r.add(n);
                    }
                }
            }
        }
        { //Gamefly
            File gameFolderTop = new File(System.getenv("USERPROFILE")
                                          + "\\My Documents\\GameFly\\games\\");
            if (gameFolderTop != null && gameFolderTop.exists() && gameFolderTop
                    .isDirectory()) {
                File[] gameFolders = gameFolderTop.listFiles();
                for (File gameFolder : gameFolders) {
                    File[] games = gameFolder.listFiles();
                    for (File game : games) {
                        String n = game.getName();
                        int index1 = n.indexOf("launcher"), index2 = n.indexOf(
                                "Launcher");
                        if (index1 < 0 && index2 < 0) {
                            r.add(n);
                        }
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
                            String n = game.getName();
                            int index1 = n.indexOf("launcher"), index2 = n
                                    .indexOf("Launcher");
                            if (index1 < 0 && index2 < 0) {
                                r.add(n);
                            }
                        }
                    }
                }
                {
                    File gameFolder = new File("C:\\Program Files\\" + providers);
                    if (gameFolder.exists() && gameFolder.isDirectory()) {
                        File[] games = gameFolder.listFiles();
                        for (File game : games) {
                            String n = game.getName();
                            int index1 = n.indexOf("launcher"), index2 = n
                                    .indexOf("Launcher");
                            if (index1 < 0 && index2 < 0) {
                                r.add(n);
                            }
                        }
                    }
                }

                {
                    File gameFolder = new File("C:\\" + providers);
                    if (gameFolder.exists() && gameFolder.isDirectory()) {
                        File[] games = gameFolder.listFiles();
                        for (File game : games) {
                            String n = game.getName();
                            int index1 = n.indexOf("launcher"), index2 = n
                                    .indexOf("Launcher");
                            if (index1 < 0 && index2 < 0) {
                                r.add(n);
                            }
                        }
                    }
                }
            }
        }
        return r;
    }

    /**
     * PRECONDITION: You are on a Windows PC, Java source 6 to compile, and
     * mainDir is not null
     *
     * @param mainDir       A directory/file on the hard drive
     * @param extensionOnly Grabs files with a certain extension only
     *
     * @return ArrayList of Files objects representing every file in the
     *         directory denoted by mainDir and it's sub directories And only finds ones
     *         with the extension specified in the parameter extensionOnly
     *
     * @exception StackOverflowError When the search becomes too large (This is
     *                               a recursive search)
     */
    public static ArrayList<Files> getAllFilesInSubDirectories(File mainDir,
                                                               String extensionOnly) {
        ArrayList<Files> r = new ArrayList<Files>();
        if (!mainDir.isDirectory()) {
            try {
                if (mainDir.getAbsolutePath().substring(mainDir
                        .getAbsolutePath().lastIndexOf(".") + 1).compareTo(
                                extensionOnly) == 0) {
                    if (mainDir.getName().indexOf("install") < 0
                        && mainDir.getName().indexOf("unin") < 0
                        && mainDir.getName().indexOf("downloader") < 0
                        && mainDir.getName().indexOf("Downloader") < 0) {
                        r.add(new Files(mainDir, 0));
                    }
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
                                    if (f.getName().indexOf("install") < 0
                                        && mainDir.getName().indexOf("unins")
                                           < 0) {
                                        r.add(new Files(f, 1));
                                    }
                                }
                            } catch (StringIndexOutOfBoundsException e) {
                            }
                        } else {
                            for (Files ff : getAllFilesInSubDirectories(f, 2)) {
                                try {
                                    if (ff.getAbsolutePath().substring(ff
                                            .getAbsolutePath().lastIndexOf(".")
                                                                       + 1)
                                            .compareTo(extensionOnly) == 0) {
                                        if (ff.getName().indexOf("install") < 0
                                            && mainDir.getName()
                                                .indexOf("unins") < 0) {
                                            r.add(ff);
                                        }
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
     * PRECONDITION: You are on a Windows PC, Java source 6 to compile, and
     * mainDir is not null
     *
     * @param mainDir   A directory/file on the hard drive
     * @param iteration always will be zero (used to calculate depth of file in
     *                  directories)
     *
     * @return ArrayList of Files objects representing every file in the
     *         directory denoted by mainDir and it's sub directories
     *
     * @exception StackOverflowError When the search becomes too large (This is
     *                               a recursive search)
     */
    public static ArrayList<Files> getAllFilesInSubDirectories(File mainDir,
                                                               int iteration) {
        ArrayList<Files> r = new ArrayList<Files>();
        if (!mainDir.isDirectory()) {
            r.add(new Files(mainDir, iteration));
        } else {
            File[] ls = mainDir.listFiles();
            if (ls != null) {
                for (File f : ls) {
                    if (f != null) {
                        if (!f.isDirectory()) {
                            r.add(new Files(f, iteration + 1));
                        } else {
                            for (Files ff : getAllFilesInSubDirectories(f,
                                    iteration + 2)) {
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
     * PRECONDITION: possibles is not null, and has at least one element
     *
     * @param possibles an Array of Files objects
     *
     * @return Files object that represents largest file in the Array denoted by
     *         the variable possibles
     */
    public static Files getLargestFile(ArrayList<Files> possibles) {
        Files r = null;
        if (possibles.size() > 0) {
            r = possibles.get(0);
            for (int i = 1; i < possibles.size(); i++) {
                Files compare = possibles.get(i);
                if (r.length() < compare.length()) {
                    r = compare;
                }
            }
        }
        return r;
    }

    /**
     * PRECONDITION: possibles is not null
     *
     * @param possibles an Array of Files objects
     * @param gameName  game name that will be used for searching for
     *                  similarities
     *
     * @return Files object that represents the earliest file in the Array
     *         denoted possibles that's name is most similar to the String gameName, and
     *         if none null is returned
     */
    public static Files getSimilarNamedFile(ArrayList<Files> possibles,
                                            String gameName) {
        for (int i = 0; i < possibles.size(); i++) {
            Files curr = possibles.get(i);
            StringTokenizer tokens = new StringTokenizer(gameName);
            while (tokens.hasMoreTokens()) {
                if (curr.getName().indexOf(tokens.nextToken()) >= 0) {
                    return curr;
                }
            }
        }
        return null;
    }

    /**
     * PRECONDITION: possibles is not null, and has at least one element
     *
     * @param possibles an Array of Files objects
     *
     * @return An Array of Files objects that represents the files with the
     *         smallest depth in the Array denoted by the variable possibles
     */
    public static ArrayList<Files> getSmallestDepth(ArrayList<Files> possibles) {
        ArrayList<Files> r = new ArrayList<Files>();
        if (possibles.size() > 0) {
            Files first = possibles.get(0); //this is just here for efficiency
            int currDepth = first.depth;
            r.add(first);
            for (int i = 1; i < possibles.size(); i++) {
                Files curr = possibles.get(i);
                if (curr.depth < currDepth) {
                    r.clear();
                    r.add(curr);
                    currDepth = curr.depth;
                } else if (curr.depth == currDepth) {
                    r.add(curr);
                }
            }
        }
        return r;
    }
}

class Files extends File { //this class is used for keeping track of the depth of a file on a specfified directory

    public int depth;

    Files(String path, int depth) {
        super(path);
        this.depth = depth;
    }

    Files(File f, int depth) {
        super(f.getAbsolutePath());
        this.depth = depth;
    }
}
