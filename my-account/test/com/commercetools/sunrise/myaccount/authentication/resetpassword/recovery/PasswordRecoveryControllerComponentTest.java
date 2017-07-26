package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import com.commercetools.sunrise.framework.template.engine.TemplateEngine;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels.PasswordResetEmailPageContent;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels.PasswordResetEmailPageContentFactory;
import io.commercetools.sunrise.email.EmailSender;
import io.commercetools.sunrise.email.MessageEditor;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import play.mvc.Call;
import play.mvc.Http;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

/**
 * Unit test for {@link PasswordRecoveryControllerComponent}.
 */
public class PasswordRecoveryControllerComponentTest {
    @Mock
    private ResetPasswordReverseRouter resetPasswordReverseRouter;
    @Mock
    private PasswordResetEmailPageContentFactory passwordResetEmailPageContentFactory;
    @Mock
    private TemplateEngine templateEngine;
    @Mock
    private EmailSender emailSender;
    @Mock
    private CustomerToken customerToken;
    @Mock
    private Http.Context context;
    @Mock
    private Http.Request request;
    @Mock
    private Call call;

    @Captor
    private ArgumentCaptor<MessageEditor> messageEditorCaptor;

    @InjectMocks
    private PasswordRecoveryControllerComponent passwordRecoveryControllerComponent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Http.Context.current.set(context);
    }

    @Test
    public void sendRecoveryEmail() throws Exception {
        final String customerEmail = "test@example.com";
        final CustomerCreatePasswordTokenCommand customerCreatePasswordTokenCommand =
                CustomerCreatePasswordTokenCommand.of(customerEmail);

        final PasswordResetEmailPageContent passwordResetEmailPageContent = new PasswordResetEmailPageContent();
        final String emailContent = "email-content";

        WHEN:
        {
            final String tokenValue = "test-token";

            when(customerToken.getValue()).thenReturn(tokenValue);
            when(context.request()).thenReturn(request);
            final String passwordResetLink = "http://my.password";
            when(call.absoluteURL(request)).thenReturn(passwordResetLink);
            when(resetPasswordReverseRouter.resetPasswordPageCall(tokenValue)).thenReturn(call);

            passwordResetEmailPageContent.setPasswordResetUrl(passwordResetLink);
            final String emailSubject = "email-subject";
            passwordResetEmailPageContent.setSubject(emailSubject);

            when(passwordResetEmailPageContentFactory.create(passwordResetLink)).thenReturn(passwordResetEmailPageContent);
            when(templateEngine.render(eq("password-reset-email"), notNull())).thenReturn(emailContent);
            when(emailSender.send(messageEditorCaptor.capture())).thenReturn(CompletableFuture.completedFuture(null));
        }

        passwordRecoveryControllerComponent.onCustomerCreatePasswordTokenCommand(customerCreatePasswordTokenCommand);
        passwordRecoveryControllerComponent.onCustomerTokenCreated(customerToken);

        THEN:
        {
            final MessageEditor messageEditor = messageEditorCaptor.getValue();

            final MimeMessage mimeMessage = mock(MimeMessage.class);
            messageEditor.edit(mimeMessage);

            verify(mimeMessage).setRecipients(Message.RecipientType.TO, customerEmail);
            verify(mimeMessage).setSubject(passwordResetEmailPageContent.getSubject(), "UTF-8");
            verify(mimeMessage).setContent(emailContent, "text/html");
        }
    }
}
