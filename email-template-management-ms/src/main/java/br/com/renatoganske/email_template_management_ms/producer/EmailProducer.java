//package br.com.renatoganske.email_template_management_ms.producer;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class EmailProducer {
//
//    final RabbitTemplate rabbitTemplate;
//
//    @Value(value = "${broker.queue.email.name}")
//    private String routingKey;
//
//    public void publishMessageEmail(UserModel userModel) {
//        var emailDto = new EmailDto();
//        emailDto.setUserId(userModel.getUserId());
//        emailDto.setEmailTo(userModel.getEmail());
//        emailDto.setSubject("Cadastro realizado com sucesso!");
//        emailDto.setText(userModel.getEmail() + ", seja bem vindo(a)! \nAgradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma!");
//
//        rabbitTemplate.convertAndSend("", routingKey, emailDto);
//    }
//}