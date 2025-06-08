# Используем официальный образ Node.js
FROM node:18

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем package.json и package-lock.json (если есть)
COPY package*.json ./

COPY sonar-project.properties . 

# Устанавливаем зависимости с использованием npm ci
RUN npm ci

# Копируем весь исходный код в контейнер
COPY . .

# Запускаем сборку (если у вас есть скрипт build в package.json)
RUN npm run build

# По умолчанию запускаем приложение (например, start)
CMD ["npm", "start"]