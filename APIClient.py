import requests
import json

class APIClient:
    def __init__(self, base_url):
        self.base_url = base_url

    def get_data(self, endpoint):
        url = f"{self.base_url}/{endpoint}"
        response = requests.get(url)
        
        if response.status_code == 200:
            return response.json()
        else:
            raise Exception(f"Request failed with status code: {response.status_code}")

# Exemplo de uso da classe APIClient
base_url = "https://dataengineer.help/api/b9dd9efdee38083cce85ad665d786880/day/api.php"  # URL da API fornecida
client = APIClient(base_url)

id_to_check = 1
data_list = []

try:
    while True:
        data = client.get_data(f"?id={id_to_check}")
        
        if not data:
            break
        
        data_list.append(data)
        id_to_check += 1

except Exception as e:
    print(f"Erro: {e}")

# Salvar em um arquivo
with open("dados_from_python.json", "w") as file:
    json.dump(data_list, file, indent=4)

