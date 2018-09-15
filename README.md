# Dictionary-LT-generator

This is a tool to generate a dictionary of lithuanian passwords, which can be used for dictionary attacks on lithuanian passwords. You need to have at least one basic dictionary and understanding of what you are trying to achieve. Without basic dictionary, this application is useless and it is your responsibility to get or create it.

__DISCLAIMER__: I take no responsibility for any misuse of this application. It was created only for penetration testing and nothing else.

<img src="http://i63.tinypic.com/akg9q1.png">

## What does it actually do?

Consider you have a file `c:\input_dictionary.txt` with the following contents (yeah, literally 2 words for the sake of simplicity):
```
erikas
petras
```

And you want to have a complete dictionary like this:
```
Petras
petras
Petro
petro
Petras10
Petras42
petras10
petras42
Petro10
Petro42
petro10
petro42
Erikas
erikas
Eriko
eriko
Erikas10
Erikas42
erikas10
erikas42
Eriko10
Eriko42
eriko10
eriko42
```

And this is exactly what this app does. You can literally create huge dictionaries out of few basic words, which are suitable for dictionary attacks on lithuanian passwords.

## How do I use it?

### Checkboxes
Hover the mouse over any checkbox to understand what it does. The text next to checkboxes is quite self explanatory and easy to understand.

### Textareas
1. __Endings textarea__ - these endings must be provided in a form of `change_what=>change_to`. Separated by new line. You should not edit this textarea, unless you need to change some very rare endings of some words. P.S. If you find such words - create issue on Github for me to fix this.
2. __Append words textarea__ - these must be separated by new line. Mostly used for numbers, but you can append anything you want, like `###` or `-admin`. Depends on what you are trying to achieve.
