from flask import jsonify
from flask_mysqldb import MySQL

# Database Details
def init(app):
    app.config['MYSQL_HOST'] = '34.101.67.26'
    app.config['MYSQL_USER'] = 'coba'
    app.config['MYSQL_PASSWORD'] = 'capstonelogue'
    app.config['MYSQL_DB'] = 'logueuser'
    return MySQL(app)

# Function Register
def register(mysql, fullname, username, email, password):
    cursor = mysql.connection.cursor()
    account = cursor.execute("SELECT * FROM accounts WHERE username=(%s)", (username,))
    if account > 0:
        return jsonify({"msg": "username already exist"}), 401
    cursor.execute("INSERT INTO accounts(fullname, username, email, password) VALUES (%s, %s, %s, %s)", (fullname, username, email, password))
    mysql.connection.commit()
    id = cursor.lastrowid
    cursor.execute("SELECT * FROM accounts WHERE id=(%s)", (id,))
    accountdetail = cursor.fetchall()
    cursor.close()
    return jsonify({
        "msg" : "Registration Successful!",
        "data" : accountdetail
    })

# Function Login
def login(mysql, username, password):
    cursor = mysql.connection.cursor()
    account = cursor.execute("SELECT * FROM accounts WHERE username=(%s) AND password=(%s)",(username, password))
    if account > 0:
            login = cursor.fetchall()
            cursor.close()
            return login
    cursor.close()
    return ""

def checkAccount(mysql):
    cursor = mysql.connection.cursor()
    cursor.execute("SELECT * FROM accounts")
    account = cursor.fetchall()
    cursor.close()
    return account
