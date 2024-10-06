# Comprehensive Guide to VS Code Extensions

## 1. vscode-icons

**Purpose**: Enhances the Visual Studio Code file explorer with file-specific icons.

**Key features**:

- Adds icons to files and folders based on file type and name
- Supports custom icon associations
- Includes icons for hundreds of file types and popular frameworks
- Customizable icon sets

**Usage**: After installation, select the icon theme by going to File > Preferences > File Icon Theme and choosing "VSCode Icons".

## 2. Prettier - Code formatter

**Purpose**: An opinionated code formatter that supports many languages.

**Key features**:

- Supports multiple languages (JavaScript, TypeScript, CSS, HTML, JSON, etc.)
- Configurable through .prettierrc file or VS Code settings
- Can be integrated with ESLint
- Formats code on save or through manual command

**Example Configuration and Usage**:

```javascript
// .prettierrc
{
  "semi": true,
  "trailingComma": "all",
  "singleQuote": true,
  "printWidth": 80,
  "tabWidth": 2
}

// Example JavaScript file before formatting
const user={name:'John Doe',age:30,isAdmin:false,interests:['coding','reading','hiking']};

function calculateTotal(items){
  return items.reduce((total,item)=>total+item.price,0)
}

// After Prettier formatting (assuming above .prettierrc configuration)
const user = {
  name: 'John Doe',
  age: 30,
  isAdmin: false,
  interests: ['coding', 'reading', 'hiking'],
};

function calculateTotal(items) {
  return items.reduce((total, item) => total + item.price, 0);
}
```

**Usage in VS Code**:

1. Install Prettier extension
2. Set up .prettierrc in your project root (optional)
3. Format document: Shift + Alt + F
4. Or enable "Format on Save" in VS Code settings

## 3. Path Intellisense

**Purpose**: Autocompletes filenames in your code.

**Key features**:

- Suggests files and folders in your workspace
- Works with relative and absolute paths
- Customizable through VS Code settings

**Example usage**:

```javascript
import { Component } from "./comp"; // It will suggest 'components/Button.js' as you type
```

## 4. npm Intellisense

**Purpose**: Autocompletes npm module names in import statements.

**Key features**:

- Suggests installed npm packages
- Works with require() and import statements
- Can be configured to show only devDependencies

**Example usage**:

```javascript
import React from "re"; // It will suggest 'react' as you type
const lodash = require("lo"); // It will suggest 'lodash' as you type
```

## 5. JavaScript (ES6) code snippets

**Purpose**: Provides snippets for common ES6+ syntax.

**Key features**:

- Quick insertion of common JavaScript patterns
- Supports modern JavaScript syntax
- Customizable snippets

**Common Snippets**:

```javascript
// Console log (clg)
console.log();

// Arrow function (anfn)
(params) => {};

// Async function (afn)
async function name(params) {}

// Destructuring assignment (dob)
const { propertyName } = objectToDestruct;

// For of loop (fof)
for (const item of object) {
}

// Import statement (imp)
import moduleName from "module";

// Class definition (cls)
class ClassName {
  constructor(params) {}
}

// Try-catch block (tc)
try {
} catch (error) {}
```

**To use these snippets**:

1. Type the shortcode (e.g., 'clg')
2. Press Tab or Enter to expand the snippet
3. Fill in the placeholders as needed

## 6. Cucumber (Gherkin) Full Support

**Purpose**: Provides comprehensive support for writing and running Cucumber tests.

**Key features**:

- Syntax highlighting for Gherkin files
- Autocomplete for steps
- Go to definition for steps
- Snippets for Gherkin syntax

**Example Gherkin feature file**:

```gherkin
Feature: Online Shopping Cart

  Scenario: Adding an item to the cart
    Given I am on the product page for "Wireless Mouse"
    When I click the "Add to Cart" button
    Then the item "Wireless Mouse" should be in my cart
    And the cart total should be updated

  Scenario: Removing an item from the cart
    Given I have "Wireless Keyboard" in my cart
    When I click the "Remove" button next to "Wireless Keyboard"
    Then "Wireless Keyboard" should be removed from my cart
    And the cart total should be updated
```

## 7. Code Runner

**Purpose**: Allows running code snippets or files for multiple languages directly within VS Code.

**Key features**:

- Supports over 40 languages
- Customizable run commands
- Output panel for results
- Code snippet running

**Usage**: Select your code and use the command palette (Ctrl+Alt+N) or right-click menu to run the code.

## 8. gitignore

**Purpose**: Provides templates and language support for .gitignore files.

**Key features**:

- Templates for various project types
- Syntax highlighting for .gitignore files
- Autocompletion for common ignore patterns

**Example .gitignore for a Node.js project**:

```gitignore
# Dependency directories
node_modules/

# Optional npm cache directory
.npm

# Optional eslint cache
.eslintcache

# dotenv environment variables file
.env

# Production build files
/dist

# Logs
logs
*.log
npm-debug.log*

# OS generated files
.DS_Store
Thumbs.db
```

## 9. DotENV

**Purpose**: Adds support for .env files used to store environment variables.

**Key features**:

- Syntax highlighting for .env files
- Autocompletion for common environment variables
- Linting for common .env file issues

**Example .env file**:

```
# Database configuration
DB_HOST=localhost
DB_USER=myuser
DB_PASS=mypassword
DB_NAME=mydatabase

# API keys
STRIPE_SECRET_KEY=sk_test_1234567890abcdefghijklmnop
GOOGLE_MAPS_API_KEY=AIzaSyBIwzALxUPNbatRBj3oPePuj-Gr3_ZEfg

# Application settings
NODE_ENV=development
PORT=3000
DEBUG=true
```

## 10. Surround With

**Purpose**: Allows quickly surrounding selected text with various syntaxes.

**Key features**:

- Works with multiple languages
- Customizable surround snippets
- Keyboard shortcuts for common surrounds

**Example usage** (in JavaScript):

1. Select the text you want to surround
2. Press Ctrl+Shift+P to open the command palette
3. Type "Surround With" and choose the appropriate option

Before:

```javascript
"Hello, World!";
```

After (surround with console.log):

```javascript
console.log("Hello, World!");
```
