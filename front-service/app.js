import express from "express";
import { join } from "path";
import {router} from "./routes/index.js";

const PORT = process.env.PORT || 1001;

const app = express();

app.set("views", join(".", "views"));
app.set("view engine", "ejs");

app.use(express.static(join(".", "public")));
app.use("/", router);
app.use("/addItem", router);
app.use("/modifyItem", router);

app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`);
});
