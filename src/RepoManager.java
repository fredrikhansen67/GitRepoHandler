/**
 * Created by fredrik on 2017-11-17.
 */
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class RepoManager {

    private static final Logger LOG = Logger.getLogger( RepoManager.class.getName() );

    public static Repository openJGitRepository() throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        return builder
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
    }

    public static Repository createNewRepository() throws IOException {
        // prepare a new folder
        String path = "C:"+File.separator+"aaa"+File.separator+"git2"+File.separator+"mytest";
        //String workingDirectory = System.getProperty("user.dir");
        //File localPath = File.createTempFile("KAlaset", "");
        //File localPath = File.createTempFile("TestGitRepository", "");
        //File localPath = File.createTempFile("repo", "", new File(path));
        try{
            deleteRecursive(new File(path));
        }catch(Exception e){}

        LOG.log(Level.INFO, " ### localPath :"+path+"\n");
        // create the

        Repository repository = FileRepositoryBuilder.create(new File(path, ".git"));
        repository.create();

        return repository;
    }
    public static void deleteRecursive(File fileOrDirectory) {
        try {
            if (fileOrDirectory.isDirectory())
                for (File child : fileOrDirectory.listFiles())
                    deleteRecursive(child);

            fileOrDirectory.delete();
        }catch(Exception e){throw e;}
    }

}