# frontend/Dockerfile
FROM node:18-alpine

WORKDIR /app

# Kopiere package.json und package-lock.json
COPY package*.json ./

# Installiere Abhängigkeiten
RUN npm install

# Kopiere den restlichen Quellcode
COPY . .

# Port freigeben
EXPOSE 3000

# Starte den Frontend-Server
CMD ["npm", "start"]