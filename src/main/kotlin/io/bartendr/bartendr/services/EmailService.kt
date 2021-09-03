package io.bartendr.bartendr.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import javax.mail.internet.MimeMessage

@Service
class EmailService {
    val fromAddress: String = "admin@bartendr.io"

    @Autowired
    lateinit var javaMailSender: JavaMailSender

    fun sendHtmlMessage(to: String, subject: String, htmlBody: String) {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")
        helper.setFrom(fromAddress)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(htmlBody)
        javaMailSender.send(message)
    }
}