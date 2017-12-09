#!/bin/sh
set -eo pipefail

LOG_LEVEL=${server_LOG_LEVEL:-INFO}

case "$1" in
    "web")         exec su-exec server gunicorn django_project.wsgi --bind 0.0.0.0:8000;;
    "celery")      exec su-exec server celery -A django_project worker;;
    "beat")        exec su-exec server celery -A django_project beat;;
esac

exec "$@"
