/**
 * Created by fredrik on 2017-11-17.
 */
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class Helper {

    private static final Logger LOG = Logger.getLogger( Helper.class.getName() );

    public static Repository openJGitRepository() throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        return builder
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
    }

    public static Repository createNewRepository() throws IOException {
        // prepare a new folder
        String path = "C:"+File.separator+"aaa"+File.separator+"git";
        String workingDirectory = System.getProperty("user.dir");
        //File localPath = File.createTempFile("KAlaset", "");
        //File localPath = File.createTempFile("TestGitRepository", "");
        File localPath = File.createTempFile("tempo", "txt", new File(path));
        if(!localPath.delete()) {
            LOG.log(Level.INFO, "*** Kunde inte deleta ***");
            throw new IOException("Could not delete temporary file " + localPath);
        }

        LOG.log(Level.INFO, " ### localPath :"+localPath+"\n"+workingDirectory+"\n");
        // create the

        Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
        repository.create();

        return repository;
    }

}