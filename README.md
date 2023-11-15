# websocket-service
# To run project
# First create config.yaml any where we want
    channels:
      - chat
      - one
      - two
      - three
    websocket:
      pingPeriod: 15
      timeout: 15
      masking: false
# Second create $CONFIG_PATH via terminal
      mkdir $CONFIG_PATH
      export CONFIG_PATH=path_directory_of_config.yaml
# Third run projet by commandline 
      ./websocket-service
      After run success will response : http://0.0.0.0:8080
      ws://localhost:8080/chat **to connect to chanel chat**
      http://localhost:8080/notify/chat **to post data to chanel chat**
      
Some step to build native image: 
https://docs.google.com/document/d/1uL5b2EZJz_674Y3xw5vgGEHg6I7Jxv8PcgPFvRMTFO4/edit
