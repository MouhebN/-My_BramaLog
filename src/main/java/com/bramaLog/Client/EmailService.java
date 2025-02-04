package com.bramaLog.Client;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendCredentials(String toEmail, String clientName, String username, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 🛠 Set the sender email (must match your SMTP username)
            helper.setFrom("your-email@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Welcome to BramaLog – Your Account Credentials");

            // 🛠 Improved HTML Email Template
            String htmlContent = String.format("""
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px; background-color: #f9f9f9;">
                    
                    <div style="text-align: center; padding: 10px 0;">
                        <h2 style="color: #2C3E50;">Welcome to <span style="color: #3498DB;">BramaLog</span></h2>
                    </div>
                    
                    <div style="background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);">
                        <p style="font-size: 16px; color: #333;">
                            Dear <strong>%s</strong>,
                        </p>
                        
                        <p style="font-size: 16px; color: #333;">
                            Your account has been successfully created. Here are your login credentials:
                        </p>

                        <table style="width: 100%%; border-collapse: collapse; margin-top: 15px;">
                            <tr>
                                <td style="background-color: #3498DB; color: white; padding: 10px; border-radius: 5px 0 0 5px;"><strong>Username:</strong></td>
                                <td style="background-color: #ECF0F1; padding: 10px; border-radius: 0 5px 5px 0;">%s</td>
                            </tr>
                            <tr>
                                <td style="background-color: #3498DB; color: white; padding: 10px; border-radius: 5px 0 0 5px;"><strong>Password:</strong></td>
                                <td style="background-color: #ECF0F1; padding: 10px; border-radius: 0 5px 5px 0;">%s</td>
                            </tr>
                        </table>

                        <p style="font-size: 16px; color: #333; margin-top: 20px;">
                            Please change your password upon first login for security reasons.
                        </p>

                        <div style="text-align: center; margin-top: 20px;">
                            <a href="https://bramalog.com/login" style="background-color: #3498DB; color: white; padding: 12px 20px; border-radius: 5px; text-decoration: none; font-size: 16px;">Login Now</a>
                        </div>
                    </div>

                    <div style="text-align: center; font-size: 14px; color: #777; margin-top: 20px;">
                        <p>If you have any questions, contact our support team at <a href="mailto:support@bramalog.com">support@bramalog.com</a>.</p>
                        <p>Best regards, <br><strong>BramaLog Team</strong></p>
                    </div>

                </div>
                """, clientName, username, password);

            // Set email content as HTML
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("✅ Styled Email Sent Successfully to " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to send email to " + toEmail);
        }
    }
}
