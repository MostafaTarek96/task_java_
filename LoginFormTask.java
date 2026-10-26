import java.awt.*;
import java.awt.event.*;

class User {

    // Todo: Add The Following Fields : username, password,role
	
	private String username;
    	private String password;
    	private String role;

    User(String username, String password, String role) {
	this.username = username;
        this.password = password;
        this.role = role;
    }

    // TODO: add getters
	public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }
}

class WelcomePage extends Frame {

    WelcomePage(User user) {
        // TODO: build the page that says "Hello, username" and the role of the user

	super("Welcome");

        setLayout(new GridLayout(2, 1));

        Label welcomeLabel = new Label("Hello, " + user.getUsername(), Label.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        Label roleLabel = new Label("Role: " + user.getRole(), Label.CENTER);
        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        add(welcomeLabel);
        add(roleLabel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(400, 200); 
        setLocationRelativeTo(null); 
        setVisible(true);
    }
}

 public class LoginFormTask extends Frame {


    //TODO: add list of users to seed
	private User[] users;
    	private int failedAttempts = 0;

    public LoginFormTask() {
        super("Login");
        loadUsers();

        setLayout(new GridBagLayout());
        Panel content = new Panel(new BorderLayout(10, 15));

        Label title = new Label("Welcome back", Label.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        content.add(title, BorderLayout.NORTH);

        Panel form = new Panel(new GridLayout(3, 2, 8, 8));
        form.add(new Label("Username:", Label.RIGHT));
        TextField username = new TextField(18);
        form.add(username);

        form.add(new Label("Password:", Label.RIGHT));
        TextField password = new TextField(18);
        password.setEchoChar('*');
        form.add(password);

        form.add(new Label("Role:", Label.RIGHT));
        Choice role = new Choice();
        role.add("Student");
        role.add("TA");
        role.add("Professor");
        form.add(role);

        content.add(form, BorderLayout.CENTER);

        Panel buttons = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        Button cancel = new Button("Cancel");
        Button login = new Button("Login");
        buttons.add(cancel);
        buttons.add(login);
        content.add(buttons, BorderLayout.SOUTH);

        add(content);

        login.addActionListener(e -> {
            // TODO: the login logic

		String uname = username.getText().trim();
            String pass = password.getText().trim();
            String selectedRole = role.getSelectedItem();

            if (uname.isEmpty() || pass.isEmpty()) {
                new Dialog(this, "Error", true) {
                    {
                        setLayout(new FlowLayout());
                        add(new Label("Please fill in all fields."));
                        Button ok = new Button("OK");
                        ok.addActionListener(ev -> dispose());
                        add(ok);
                        setSize(250, 100);
                        setLocationRelativeTo(null);
                        setVisible(true);
                    }
                };
                return;
            }

            User found = findUser(uname);
            if (found != null && found.getPassword().equals(pass) && found.getRole().equals(selectedRole)) {
                dispose();
                new WelcomePage(found);
            } else {
                failedAttempts++;
                if (failedAttempts >= 3) {
                    login.setVisible(false);
                    new Dialog(this, "Error", true) {
                        {
                            setLayout(new FlowLayout());
                            add(new Label("Too many failed attempts. Login disabled."));
                            Button ok = new Button("OK");
                            ok.addActionListener(ev -> dispose());
                            add(ok);
                            setSize(350, 100);
                            setLocationRelativeTo(null);
                            setVisible(true);
                        }
                    };
                } else {
                    new Dialog(this, "Error", true) {
                        {
                            setLayout(new FlowLayout());
                            add(new Label("Invalid username, password, or role."));
                            Button ok = new Button("OK");
                            ok.addActionListener(ev -> dispose());
                            add(ok);
                            setSize(300, 100);
                            setLocationRelativeTo(null);
                            setVisible(true);
                        }
                    };
                }
            }
        });

        cancel.addActionListener(e -> {
            // TODO: close the window
		dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { dispose(); }
        });
        setSize(420, 340);
        setLocationRelativeTo(null);

    }

    private void loadUsers() {
        // TODO: add at least 3 users to the list one for each role

	users = new User[] {
                new User("Mostafa", "m123", "Student"),
                new User("Mahmoud", "a123", "TA"),
                new User("Ahmed", "h123", "Professor")
        };
    }

    private User findUser(String name) {
        // TODO: search the list for the user

	for (int i = 0; i < users.length; i++) {
            if (users[i].getUsername().equals(name)) {
                return users[i];
            }
        }
        return null;
    }


    public static void main(String[] args) {
        new LoginFormTask().setVisible(true);

    }
}
