
VENV := .venv

slides: slides/index.html

slides/index.html: slides/slides.md
	cd slides; \
	../$(VENV)/bin/slidedeck render

init:
	virtualenv --python /usr/bin/python2 ./$(VENV)
	./$(VENV)/bin/pip install slidedeck
	./$(VENV)/bin/slidedeck create ./slides
