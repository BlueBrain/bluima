neuroNER
========

named entity recognizer for neuronal cells, based on UIMA Ruta rules


### Use

TODO how to apply NER to simple text

### Developer Install

NeuroNER uses Ruta ([site](https://uima.apache.org/ruta.html), [documentation](https://uima.apache.org/d/ruta-current/tools.ruta.book.html)), a workbench to develop and test NLP rules.

- [Download Eclipse](http://eclipse.org/downloads/)
- Install RUTA plugin
    - Help > Install New Software...
        - Work with: `http://www.apache.org/dist/uima/eclipse-update-site/`
        - Add...
            - Name: RUTA, OK
        - Select `Apache UIMA Eclipse tooling and runtime support` and `Apache UIMA Ruta`
            - Next, Next, Finish
- Open Ruta
    - Window > Open Perspective > Other...
        - Select: Uima Ruta
- Install neuroNER
    - File > Import
        - General > Existing Project into Workspace, Next
        - Select archive file
        - Browse... to your neuroNER folder
            - Finish
    - The project should appear in your *Script Browser* on the left

- Now let's apply the rules on some text.
    - Open script `script/neuroNER/NeuroNER.ruta`. This is where rules are defined. Check Ruta documentation to understand the syntax.
    - Right click, Debug As, UIMA Ruta
    - Open Console view to see progress, wait until it's `<terminated>`
- View results
    - Open files in `output/`
        - Select Type System `NeuroNERTypeSystem.xml`
    - Click on Eclipse View `Annotation Browser View` (tabed window on the right)
        - Select checkboxes, to reveal the annotations we added.
- Inspect results
    - This allows to see which rules have been applied to which sentence.
    - Window > Open Perspective > Ruta Explain
    - See what rules have been applied
        - Click on Eclipse View `Applied Rules`

