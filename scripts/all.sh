sudo chmod +x run-payments-api-db.sh
sudo chmod +x run-authentication-api.sh
sudo usermod -aG docker pallav
sudo service docker restart
./run-authentication-api.sh
./run-payments-api-db.sh   

docker restart payments-api-db authentication-api

