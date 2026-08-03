# Centria Platform

## Frontend Architecture

**Project:** Centria Platform

**Author:** CHENTOUF Bilal

**Version:** 1.0

**Created:** 2026-08-03

**Status:** Active

---

# Purpose

This document defines the frontend architecture of the Centria Platform.

Its purpose is to keep the project organized, scalable, maintainable, and scalable as the application grows.

All frontend development should follow the principles described in this document.

---

# CSS Directory Structure

assets/
└── css/
    ├── core/
    ├── layout/
    ├── components/
    ├── patterns/
    ├── pages/
    ├── standalone/
    └── themes/

---

# Folder Responsibilities

## core/

Contains the foundation of the design system.

Examples:

- Variables
- Colors
- Typography
- Reset
- Accessibility
- Global Utilities

Current files:

- core.css

---

## layout/

Contains the application layout.

Examples:

- Sidebar
- Header
- App Layout

Current files:

- app-layout.css
- header.css
- sidebar.css

---

## components/

Contains reusable UI components.

Examples:

- Buttons
- Cards
- Tables
- Forms
- Inputs
- Badges
- Modals
- Pagination

Current status:

Not implemented yet.

---

## patterns/

Contains reusable UI patterns.

Examples:

- CRUD Layout
- Dashboard Sections
- Statistics Blocks
- Toolbars

Current status:

Reserved for future versions.

---

## pages/

Contains styles specific to each page.

Current files:

- accueil.css
- centres.css
- payments.css

---

## standalone/

Contains standalone pages that do not use the dashboard layout.

Current files:

- login.css
- add-centre.css
- success-page.css

---

## themes/

Contains future themes.

Examples:

- Light Theme
- Dark Theme
- Centria Theme

Current status:

Reserved.

---

# Design Principles

The Centria Platform follows these principles:

1. Separation of responsibilities.
2. Reusable components.
3. Clean architecture.
4. Responsive by default.
5. RTL and LTR support.
6. Maintainability over shortcuts.

---

# Status

Frontend Architecture

Version: 1.0

Status: Active

---

# Copyright

© 2026 CHENTOUF Bilal

Centria Platform

All rights reserved.