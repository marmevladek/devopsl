import { Router } from "express";
export const router = Router();

router.get("/", (req, res) => {
  res.render("index", {
    title: "Магазин",
  });
});

router.get("/createItem", (req, res) => {
  res.render("createItem", {
    title: "Магазин",
  });
});

router.get("/updateItem", (req, res) => {
  res.render("updateItem", {
    title: "Магазин",
  });
});

router.get("/readItem", (req, res) => {
  res.render("Магазин", {
    title: "Read",
  });
});