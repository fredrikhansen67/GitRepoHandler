/**
 * Created by fredrik on 2017-11-17.
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;

/**
 * Simple snippet which shows how to commit all files
 *
 *
 */
public class CommitAll {

    public static void main(String[] args) throws IOException, GitAPIException {
        // prepare a new test-repository
        try (Repository repository = RepoManager.createNewRepository()) {
            try (Git git = new Git(repository)) {
                // create the file
                File myfile = new File(repository.getDirectory().getParent(), "testfile");
                if(!myfile.createNewFile()) {
                    throw new IOException("Could not create file " + myfile);
                }

                // Stage all files in the repo including new files
                git.add().addFilepattern(".").call();

                // and then commit the changes.
                git.commit()
                        .setMessage("Commit all changes including additions")
                        .call();

                try(PrintWriter writer = new PrintWriter(myfile)) {
                    writer.append("Hello, world!");
                }

                // Stage all changed files, omitting new files, and commit with one command
                git.commit()
                        .setAll(true)
                        .setMessage("Commit changes to all files")
                        .call();


                System.out.println("Committed all changes to repository at " + repository.getDirectory());
            }
        }
    }
}
