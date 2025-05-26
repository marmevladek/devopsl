package ru.devopsl.backendservice.config.init;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class DataInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void insertDataIfEmpty() {
        Integer countOfProducts = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products", Integer.class);
        Integer countOfCategories = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM categories", Integer.class);

        if (countOfProducts == null || countOfProducts == 0 || countOfCategories == null || countOfCategories == 0) {
            insertInitialData();
        } else {
            System.out.println("Данные уже есть, вставка пропущена.");
        }
    }

    private void insertInitialData() {
        System.out.println("Вставляем стартовые данные...");

        String[] categories = {"Электроника", "Книги", "Одежда", "Мебель", "Игрушки", "Продукты", "Инструменты"};
        for (String category : categories) {
            jdbcTemplate.update("INSERT INTO categories (name) VALUES (?)", category);
        }

        for (int catId = 1; catId <= categories.length; catId++) {
            for (int i = 1; i <= 10; i++) {
                String name = "";
                String description = "";
                String fullDescription = "";
                double price = 0.0;
                String linkImage = "";
                String phoneNumber = "+7 (900) 000-00" + String.format("%02d", i);
                String email = "contact" + i + "@example.com";
                String createdAtStr = "2024-05-01 10:00:00";

                switch (catId) {
                    case 1:
                        name = switch (i) {
                            case 1 -> "Смартфон Samsung Galaxy S21";
                            case 2 -> "Ноутбук Apple MacBook Pro 13\"";
                            case 3 -> "Планшет Huawei MatePad 11";
                            case 4 -> "Смарт-часы Apple Watch Series 7";
                            case 5 -> "Наушники Sony WH-1000XM4";
                            case 6 -> "Телевизор LG OLED55CX";
                            case 7 -> "Фитнес-браслет Xiaomi Mi Band 6";
                            case 8 -> "Игровая консоль Sony PlayStation 5";
                            case 9 -> "Фотоаппарат Canon EOS R5";
                            case 10 -> "Электронная книга Amazon Kindle Paperwhite";
                            default -> "Электронный товар";
                        };
                        description = switch (i) {
                            case 1 -> "Флагманский смартфон с отличной камерой и производительностью.";
                            case 2 -> "Мощный ноутбук для профессионалов и креативщиков.";
                            case 3 -> "Планшет с высоким разрешением экрана и стилусом.";
                            case 4 -> "Смарт-часы с множеством функций для здоровья и фитнеса.";
                            case 5 -> "Беспроводные наушники с активным шумоподавлением.";
                            case 6 -> "Телевизор с OLED-дисплеем и поддержкой 4K.";
                            case 7 -> "Фитнес-браслет с мониторингом активности и сна.";
                            case 8 -> "Игровая консоль нового поколения с поддержкой 4K-гейминга.";
                            case 9 -> "Полнокадровая камера для профессиональной фотографии.";
                            case 10 -> "Электронная книга с подсветкой и высоким разрешением.";
                            default -> "Описание электронного товара.";
                        };
                        fullDescription = description + " Отличный выбор для современных пользователей.";
                        price = 50000 + i * 5000;
                        linkImage = "http://example.com/images/electronics_" + i + ".jpg";
                        break;
                    case 2:
                        name = switch (i) {
                            case 1 -> "Книга \"1984\" Джордж Оруэлл";
                            case 2 -> "Книга \"Мастер и Маргарита\" Михаил Булгаков";
                            case 3 -> "Книга \"Преступление и наказание\" Фёдор Достоевский";
                            case 4 -> "Книга \"Война и мир\" Лев Толстой";
                            case 5 -> "Книга \"Анна Каренина\" Лев Толстой";
                            case 6 -> "Книга \"Идиот\" Фёдор Достоевский";
                            case 7 -> "Книга \"Братья Карамазовы\" Фёдор Достоевский";
                            case 8 -> "Книга \"Доктор Живаго\" Борис Пастернак";
                            case 9 -> "Книга \"Тихий Дон\" Михаил Шолохов";
                            case 10 -> "Книга \"Двенадцать стульев\" Илья Ильф и Евгений Петров";
                            default -> "Книга";
                        };
                        description = "Классическое произведение русской литературы.";
                        fullDescription = description + " Обязательное чтение для ценителей литературы.";
                        price = 500 + i * 100;
                        linkImage = "http://example.com/images/books_" + i + ".jpg";
                        break;
                    case 3:
                        name = switch (i) {
                            case 1 -> "Футболка Nike Dri-FIT";
                            case 2 -> "Джинсы Levi's 501";
                            case 3 -> "Куртка The North Face";
                            case 4 -> "Кроссовки Adidas Ultraboost";
                            case 5 -> "Платье Zara Summer Collection";
                            case 6 -> "Рубашка H&M Slim Fit";
                            case 7 -> "Пальто Mango Classic";
                            case 8 -> "Спортивный костюм Puma";
                            case 9 -> "Шорты Reebok Training";
                            case 10 -> "Свитер Uniqlo Cashmere";
                            default -> "Одежда";
                        };
                        description = "Стильная и комфортная одежда для повседневной носки.";
                        fullDescription = description + " Подходит для любого сезона.";
                        price = 2000 + i * 500;
                        linkImage = "http://example.com/images/clothing_" + i + ".jpg";
                        break;
                    case 4:
                        name = switch (i) {
                            case 1 -> "Диван IKEA EKTORP";
                            case 2 -> "Кресло Poäng";
                            case 3 -> "Обеденный стол Norden";
                            case 4 -> "Кровать Malm";
                            case 5 -> "Шкаф Pax";
                            case 6 -> "Комод Hemnes";
                            case 7 -> "Письменный стол Bekant";
                            case 8 -> "Кухонный гарнитур Metod";
                            case 9 -> "Тумба под ТВ Besta";
                            case 10 -> "Полка Kallax";
                            default -> "Мебель";
                        };
                        description = "Функциональная и стильная мебель для вашего дома.";
                        fullDescription = description + " Легко собирается и вписывается в любой интерьер.";
                        price = 5000 + i * 1000;
                        linkImage = "http://example.com/images/furniture_" + i + ".jpg";
                        break;
                    case 5:
                        name = switch (i) {
                            case 1 -> "Конструктор LEGO City";
                            case 2 -> "Кукла Barbie Dreamhouse";
                            case 3 -> "Мягкая игрушка Teddy Bear";
                            case 4 -> "Настольная игра Monopoly";
                            case 5 -> "Пазл Ravensburger 1000 деталей";
                            case 6 -> "Игровая машинка Hot Wheels";
                            case 7 -> "Детский велосипед Stels";
                            case 8 -> "Пластилин Play-Doh";
                            case 9 -> "Радиоуправляемая машина";
                            case 10 -> "Игровая кухня Smoby";
                            default -> "Игрушка";
                        };
                        description = "Развивающая игрушка для детей разного возраста.";
                        fullDescription = description + " Помогает развивать воображение и моторику.";
                        price = 1000 + i * 300;
                        linkImage = "http://example.com/images/toys_" + i + ".jpg";
                        break;
                    case 6:
                        name = switch (i) {
                            case 1 -> "Кофе Jacobs Monarch 250г";
                            case 2 -> "Чай Greenfield Earl Grey";
                            case 3 -> "Шоколад Alpen Gold";
                            case 4 -> "Печенье Oreo";
                            case 5 -> "Сыр Пармезан 200г";
                            case 6 -> "Колбаса Докторская 500г";
                            case 7 -> "Молоко Простоквашино 1л";
                            case 8 -> "Йогурт Danone 150г";
                            case 9 -> "Хлеб Бородинский";
                            case 10 -> "Масло сливочное 180г";
                            default -> "Продукт";
                        };
                        description = "Свежий и качественный продукт питания.";
                        fullDescription = description + " Подходит для ежедневного употребления.";
                        price = 100 + i * 50;
                        linkImage = "http://example.com/images/food_" + i + ".jpg";
                        break;
                    case 7: // Инструменты
                        name = switch (i) {
                            case 1 -> "Дрель Bosch GSB 13 RE";
                            case 2 -> "Отвертка Stanley FatMax";
                            case 3 -> "Молоток Stayer 500г";
                            case 4 -> "Пила ручная Irwin";
                            case 5 -> "Шуруповерт Makita DF330D";
                            case 6 -> "Набор ключей King Tony 12 предметов";
                            case 7 -> "Рулетка измерительная 5м Matrix";
                            case 8 -> "Ножовка по дереву Gross";
                            case 9 -> "Уровень строительный Kapro 60см";
                            case 10 -> "Перфоратор DeWalt D25143K";
                            default -> "Инструмент";
                        };
                        description = "Надёжный инструмент для дома и профессионального использования.";
                        fullDescription = description + " Подходит для выполнения широкого спектра задач.";
                        price = 1000 + i * 700;
                        linkImage = "http://example.com/images/tools_" + i + ".jpg";
                        break;
                }

                jdbcTemplate.update("""
                        INSERT INTO products (
                            name, description, full_description, price,
                            link_image, phone_number, email, category_id, created_at
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """,
                        name,
                        description,
                        fullDescription,
                        price,
                        linkImage,
                        phoneNumber,
                        email,
                        catId,
                        Timestamp.valueOf(createdAtStr)
                );
            }
        }
    }
}
