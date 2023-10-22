import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

	@Autowired
	private JavaMailSender mailSender;

	@PostMapping("/sendEmail")
	public String sendEmail(@RequestParam String to, @RequestParam String authenticationCode) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Authentication Code");
		message.setText("Your authentication code is: " + authenticationCode);

		mailSender.send(message);

		return "Email sent successfully.";
	}
}
