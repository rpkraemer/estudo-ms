FROM rabbitmq:3.6.16-management

# Configuração para liberar o user guest para acessar o rabbit de qualquer lugar na rede
COPY ./rabbitmq-isolated.conf /etc/rabbitmq/rabbitmq.config

# Copia o plugin de delayed exchange para habilitá-lo na imagem
COPY ./rabbitmq_delayed_message_exchange-20171215-3.6.x.ez /plugins/rabbitmq_delayed_message_exchange-20171215-3.6.x.ez

RUN rabbitmq-plugins enable --offline rabbitmq_delayed_message_exchange