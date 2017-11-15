package com.github.app.tools;

import com.jcabi.ssh.Shell;
import com.jcabi.ssh.SshByPassword;

import java.net.InetAddress;

public class SSHClient1 {
    public static void main(String[] args) throws Exception {
        Shell shell = new Shell.Verbose(
                new SshByPassword(
                        InetAddress.getLocalHost().getHostAddress(),
                        22,
                        "xsy",
                        "xsy870712"
                )
        );
        String stdout = new Shell.Plain(shell).exec("cd /home/xsy;wget www.baidu.com;");
        System.out.println(stdout);
    }
}
