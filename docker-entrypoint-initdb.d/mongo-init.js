print("Started Adding the Users.");
db = db.getSiblingDB("inventory-app");
db.createUser({
    user: "dbuser",
    pwd: "password",
    roles: [{ role: "readWrite", db: "inventory-app" }],
});
print("End Adding the User Roles.");