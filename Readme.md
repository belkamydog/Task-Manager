# 🚀 Task Management System (TMS)

**Система управления задачами** на Java/Spring с базовым CRUD-функционалом  

## 🌟 Возможности системы

- ✅ **CRUD операции** с задачами
- 📅 **Фильтрация задач** по статусу/дате
- 👨‍💻 **Авторизация пользователей** (JWT)
- 📱 **REST API** для интеграции
- 🐳 **Docker-контейнеризация**

## 🛠 Технологический стек
![Java](https://img.shields.io/badge/Java-21-red)
![Spring](https://img.shields.io/badge/Spring_Boot-3.0-%236DB33F)
![Security](https://img.shields.io/badge/Spring_Security-6.0-%236DB33F)
![JPA](https://img.shields.io/badge/Spring_Data_JPA-3.0-%236DB33F)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-%23336791)
![Lombok](https://img.shields.io/badge/Lombok-1.18-%2300599C)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-%23BA2525)
![Gateway](https://img.shields.io/badge/Spring_Cloud_Gateway-4.0-%236DB33F)
![Lombok](https://img.shields.io/badge/Lombok-1.18-%2300599C)
![Feign](https://img.shields.io/badge/OpenFeign-4.0-%23C71A36)
![JWT](https://img.shields.io/badge/JWT-0.11-%23000000)
![Docker](https://img.shields.io/badge/Docker-20.10%2B-blue)
![Compose](https://img.shields.io/badge/Compose-1.29%2B-blueviolet)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-orange)

### 1. Клонировать репозиторий
```bash
git clone https://github.com/your-repo/task-management-system.git
```

### 2. Запустить в Docker
```bash
docker-compose up -d
```

### 3. После запуска система доступна:

#### 🔗 http://localhost:8080

#### 📚 Документация: http://localhost:8080/swagger-ui.html

### 🗂 Структура проекта
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           ├── config/       # Конфигурации
│   │           ├── controller/   # REST контроллеры
│   │           ├── model/        # Сущности
│   │           ├── repository/   # Репозитории
│   │           ├── service/      # Бизнес-логика
│   │           └── TaskApp.java  # Главный класс
│   └── resources/
│       ├── application.yml       # Настройки
```