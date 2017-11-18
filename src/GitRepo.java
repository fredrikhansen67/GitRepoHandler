import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fredrik on 2017-11-17.
 */
public class GitRepo {

    private static final Logger LOG = Logger.getLogger( GitRepo.class.getName() );

    public static void main(String[] args) throws IOException, IllegalStateException, GitAPIException {
        // prepare a new folder
        String path="C://aaa/git/";
        String gitURI = "https://github.com/fredrikhansen67/Algorithmens.git";
        LOG.log(Level.INFO, "Started");
        RepoManager repo = new RepoManager();
        repo.createNewRepository();

//        Git.cloneRepository()
//                .setURI(gitURI)
//                .setDirectory(new File("C://aaa//git//repo//"))
//                .call();
//        File localPath = File.createTempFile(path+"TestGitRepository", "");
//        if(!localPath.delete()) {
//            throw new IOException("Could not delete temporary file " + localPath);
//        }
//
//        // create the directory
//        try (Git git = Git.init().setDirectory(localPath).call()) {
//            System.out.println("Having repository: " + git.getRepository().getDirectory());
//        }
//
//        FileUtils.deleteDirectory(localPath);
    }
}
