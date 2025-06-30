FROM node:18

# Установить Java (OpenJDK 11), необходимую для sonar-scanner
RUN apt-get update && apt-get install -y openjdk-17-jre-headless && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY package*.json ./
COPY sonar-project.properties . 

RUN npm ci

COPY . .

RUN npm run build

# Установим sonar-scanner CLI глобально
RUN npm install -g sonar-scanner

# По умолчанию запускаем приложение
CMD ["npm", "start"]