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
import org.junit.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestJGit {
    private static final Logger LOG = Logger.getLogger(TestJGit.class.getName());

    private String localPath, remotePath;
    private Repository localRepo;
    private Git git;
    private String user;
    private String password;

//    public TestJGit(){
//        user = JOptionPane.showInputDialog("Enter username");
//        password = JOptionPane.showInputDialog("Enter password");
//    }


    public void getCredentials() {
        JLabel label_login = new JLabel("Username:");
        JTextField login = new JTextField();

        JLabel label_password = new JLabel("Password:");
        JPasswordField password = new JPasswordField();
        Object[] array = {label_login, login, label_password, password};

        int res = JOptionPane.showConfirmDialog(null, array, "Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (res == JOptionPane.OK_OPTION) {
            System.out.println("username: " + login.getText().trim());
            System.out.println("password: " + new String(password.getPassword()));
        }
    }

        @Before
        public void init ()throws IOException{
            //localPath = "/home/me/repos/mytest";
            localPath = "C:\\aaa\\git\\mytest\\";
            remotePath = "https://github.com/fredrikhansen67/Algorithmens.git";
            localRepo = new FileRepository(localPath + ".git");
            //getCredentials();
            git = new Git(localRepo);
        }

    @Test
    public void testClone() throws IOException, GitAPIException {
        File cat = new File("C:\\aaa\\git\\mytest");
        try {
            LOG.log(Level.INFO, "Removing old files...");
            deleteRecursive(cat);
            LOG.log(Level.INFO,"DELETING "+ cat.exists());
        } catch (Exception x) {
            LOG.log(Level.SEVERE, "Failed to remove repo");
            System.err.println(x);
        }
        if(cat.exists())
            deleteRecursive(cat);
        Git git = Git.cloneRepository()
                .setURI(remotePath)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("fredrikhansen67", "11texas11"))
                .setDirectory(new File(localPath))
                .call();

        //Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath)).call();
    }
    @Ignore
    @Test
    public void testAdd() throws IOException, GitAPIException {
        File myfile = new File(localPath + "/myfile");
        myfile.createNewFile();
        git.add().addFilepattern("myfile").call();
    }
    @Ignore
    @Test
    public void testCommit() throws IOException, GitAPIException,
            JGitInternalException {
        git.commit().setMessage("Added myfile").call();
    }

    @Ignore
    @Test
    public void testTrackMaster() throws IOException, JGitInternalException,
            GitAPIException {
        git.branchCreate().setName("master")
                .setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
                .setStartPoint("origin/master").setForce(true).call();
    }

    @Ignore
    @Test
    public void testPull() throws IOException, GitAPIException {
        git.pull().call();
    }

    @Ignore
    @Test
    public void testCreate() throws IOException {
        try {
            deleteRecursive(new File(localPath));
        } catch (Exception x) {
            System.err.println(x);
        }

        Repository newRepo = new FileRepository(localPath + ".git");
        newRepo.create();
    }

    @Ignore
    @Test
    public void testPush() throws IOException, JGitInternalException,
                GitAPIException {
            git.push().call();
    }

    @Ignore
    @Test
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
    }

    void deleteRecursive(File fileOrDirectory) {
        try {
           // LOG.log(Level.INFO, "delete :"+fileOrDirectory.getName()+" path:" + fileOrDirectory.getAbsolutePath());
            if (fileOrDirectory.isDirectory())
                for (File child : fileOrDirectory.listFiles())
                    deleteRecursive(child);

            fileOrDirectory.delete();
        }catch(Exception e){throw e;}
    }
}
