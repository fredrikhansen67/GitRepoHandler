/**
 * Created by fredrik on 2017-11-17.
 */
        import java.io.File;
        import java.io.IOException;
        import org.eclipse.jgit.api.Git;
        import org.eclipse.jgit.api.errors.GitAPIException;
        import org.eclipse.jgit.lib.Repository;

/**
 * Simple snippet which shows how to add a file to the index
 *
 *
 */
public class AddFile {

    public static void main(String[] args) throws IOException, GitAPIException {
        // prepare a new test-repository
        String path = "C:\\aaa\\git\\";
        try (Repository repository = RepoManager.createNewRepository()) {
            try (Git git = new Git(repository)) {
                // create the file
                File myfile = new File(repository.getDirectory().getParent(), "testfile");
                if(!myfile.createNewFile()) {
                    throw new IOException("Could not create file " + myfile);
                }

                // run the add-call
                git.add()
                        .addFilepattern("testfile")
                        .call();

                System.out.println("Added file " + myfile + " to repository at " + repository.getDirectory());
            }
        }
    }
}
