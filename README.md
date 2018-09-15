# Dictionary-LT-generator

<img src="http://i67.tinypic.com/53pwsz.png">

## What is it
This is a tool to generate a dictionary of lithuanian passwords, which can be used for dictionary attacks on lithuanian passwords. You need to have at least one basic dictionary and understanding of what you are trying to achieve. Without basic dictionary, this application is useless and it is your responsibility to get or create it.

__DISCLAIMER__: I take no responsibility for any misuse of this app. It was only created for penetration testing, not for actual hacking.

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
1. __Starts with uppercase__ - change first letter of each word to UPPERcase
2. __Starts with lowercase__ - change first letter of each word to lowercase
3. __Convert endings__ - change given endings for each word (e.g. `erikas` becomes `eriko` etc...)
4. __Append text__ - append given text after each word (e.g. `erikas` becomes `erikas123` or erikas` becomes `erikas!!!` etc...)

### Text areas
1. __Endings textarea__ - these endings must be provided in a form of `change_what=>change_to`. Separated by new line. you should not edit this textarea, unless you find it needed to change some rare endings of some words.
2. __Append words textarea__ - these must be separated by new line. Mostly used for numbers, but you can append anything you want, like `_bro` or `-admin`. Depends on what you are trying to achieve.

### Remaining 2 checkboxes
To understand what remaining 2 checkboxes do, you need to understand the way application process words:
1. step: Convert case of first letter.
2. step: Change endings of the words.
3. step: Append text to the end of each word.

So these checkboxes:
1. __Also include words with original endings__ - If checked, all original words generated from __1. step__ will not be added to output dictionary.
2. __Also include words without these__ - If checked, all original words generated from __2. step__ will not be added to output dictionary.
