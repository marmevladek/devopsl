import { Router } from "express";
export const router = Router();

router.get("/", (req, res) => {
  res.render("index", {
    title: "Магазин",
  });
});

router.get("/createItem", (req, res) => {
  res.render("createItem", {
    title: "Create",
  });
});

router.get("/updateItem", (req, res) => {
  res.render("updateItem", {
    title: "Modify",
  });
});

router.get("/readItem", (req, res) => {
  res.render("readItem", {
    title: "Read",
  });
});