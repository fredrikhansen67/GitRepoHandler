/**
 * Created by fredrik on 2017-11-17.
 */
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

import org.eclipse.jgit.transport.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class TestJGit {

    private String localPath, remotePath;
    private Repository localRepo;
    private Git git;

    @Before
    public void init() throws IOException {
        //localPath = "/home/me/repos/mytest";
        localPath = "C:\\aaa\\git\\mytest\\";
        remotePath = "https://github.com/fredrikhansen67/Algorithmens.git";
        //remotePath = "git@github.com:me/mytestrepo.git";
        localRepo = new FileRepository(localPath + ".git");
        git = new Git(localRepo);
    }

    /*@Test
    public void testCloneSSH() throws IOException, GitAPIException {
        final String REMOTE_URL = "git@github.com:fredrikhansen67/Algorithmens.git";
        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session ) {
                session.setUserInfo(new UserInfo() {
                    @Override
                    public String getPassphrase() {
                        return "11texas11";
                    }

                    @Override
                    public String getPassword() {return null;}
                    @Override
                    public boolean promptPassword(String message) {return false;}
                    @Override
                    public boolean promptPassphrase(String message) {return true;}
                    @Override
                    public boolean promptYesNo(String message) {return false;}
                    @Override
                    public void showMessage(String message) {}
                });
            }
        };
        File localPath = File.createTempFile("TestGitRepository", "");
        localPath.delete();
        try (Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setTransportConfigCallback(transport -> {
                    SshTransport sshTransport = (SshTransport)transport;
                    sshTransport.setSshSessionFactory( sshSessionFactory );
                })
                .setDirectory(localPath)
                .call()) {
            System.out.println("Having repository: " + result.getRepository().getDirectory());
        }
    }*/


    @Test
    public void testClone() throws IOException, GitAPIException {
        try {
            deleteRecursive(new File(localPath));
        } catch (Exception x) {
            System.err.println(x);
        }
        //"https://github.com/eclipse/jgit.git"
        Git git = Git.cloneRepository()
                .setURI(remotePath)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("fredrikhansen67", "11texas11"))
                .setDirectory(new File(localPath))
                .call();

        //Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath)).call();
    }

    @Test
    public void testAdd() throws IOException, GitAPIException {
        File myfile = new File(localPath + "/myfile");
        myfile.createNewFile();
        git.add().addFilepattern("myfile").call();
    }

    @Test
    public void testCommit() throws IOException, GitAPIException,
            JGitInternalException {
        git.commit().setMessage("Added myfile").call();
    }


    @Test
    public void testTrackMaster() throws IOException, JGitInternalException,
            GitAPIException {
        git.branchCreate().setName("master")
                .setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
                .setStartPoint("origin/master").setForce(true).call();
    }

    @Test
    public void testPull() throws IOException, GitAPIException {
        git.pull().call();
    }
    /*   @Test
    public void testCreate() throws IOException {
        try {
            deleteRecursive(new File(localPath));
        } catch (Exception x) {
            System.err.println(x);
        }

        Repository newRepo = new FileRepository(localPath + ".git");
        newRepo.create();
    }*/
    /*
        @Test
        public void testPush() throws IOException, JGitInternalException,
                GitAPIException {
            git.push().call();
        }*/
    void deleteRecursive(File fileOrDirectory) {
        try {
            if (fileOrDirectory.isDirectory())
                for (File child : fileOrDirectory.listFiles())
                    deleteRecursive(child);

            fileOrDirectory.delete();
        }catch(Exception e){throw e;}
    }
}
