// Asume que existe una variable global 'veterinarioId' con el ID del veterinario logueado
// Por ejemplo: <script>const veterinarioId = 1;</script> en el layout o antes de este script

const tablaHorarios = document.getElementById('tabla-horarios').getElementsByTagName('tbody')[0];
const formAgregar = document.getElementById('form-agregar-horario');

// Utilidad para formatear hora (HH:mm:ss a HH:mm)
function formatHora(hora) {
    if (!hora) return '';
    return hora.substring(0,5);
}

// Cargar horarios actuales del veterinario
async function cargarHorarios() {
    tablaHorarios.innerHTML = '';
    const resp = await fetch(`/api/Veterinario/${veterinarioId}/horarios`);
    if (!resp.ok) {
        tablaHorarios.innerHTML = '<tr><td colspan="4" class="text-center">No se pudieron cargar los horarios.</td></tr>';
        return;
    }
    const horarios = await resp.json();
    if (!horarios.length) {
        tablaHorarios.innerHTML = '<tr><td colspan="4" class="text-center">No tienes horarios registrados.</td></tr>';
        return;
    }
    for (const h of horarios) {
        agregarFilaHorario(h);
    }
}

// Agregar una fila a la tabla
function agregarFilaHorario(horario) {
    const fila = document.createElement('tr');
    fila.innerHTML =
        '<td>' + horario.dia_semana + '</td>' +
        '<td>' + formatHora(horario.hora_inicio) + '</td>' +
        '<td>' + formatHora(horario.hora_fin) + '</td>' +
        '<td>' +
            '<button class="btn btn-sm btn-warning me-1" onclick="editarHorario(' + horario.id + ')">Editar</button>' +
            '<button class="btn btn-sm btn-danger" onclick="eliminarHorario(' + horario.id + ')">Eliminar</button>' +
        '</td>';
    tablaHorarios.appendChild(fila);
}

// Agregar nuevo horario
formAgregar.addEventListener('submit', async function(e) {
    e.preventDefault();
    const dia_semana = formAgregar.dia_semana.value;
    const hora_inicio = formAgregar.hora_inicio.value;
    const hora_fin = formAgregar.hora_fin.value;
    // 1. Crear el bloque en horario_laboral
    const resp1 = await fetch('/api/HorarioLaboral', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ dia_semana, hora_inicio: hora_inicio+':00', hora_fin: hora_fin+':00' })
    });
    if (!resp1.ok) {
        alert('Error al crear el horario laboral');
        return;
    }
    const horario = await resp1.json();
    // 2. Asignar ese bloque al veterinario
    const resp2 = await fetch('/api/VeterinarioHorario', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            veterinario: { id: veterinarioId },
            horario: { id: horario.id }
        })
    });
    if (!resp2.ok) {
        alert('Error al asignar el horario al veterinario');
        return;
    }
    // Recargar la tabla
    cargarHorarios();
    formAgregar.reset();
});

// Eliminar horario (elimina la relación, no el bloque)
async function eliminarHorario(horarioId) {
    if (!confirm('¿Seguro que deseas eliminar este intervalo?')) return;
    // Buscar el id de la relación VeterinarioHorario
    // Necesitamos obtener la relación, así que pedimos todas y buscamos la que tenga este horario
    const resp = await fetch('/api/VeterinarioHorario');
    if (!resp.ok) {
        alert('No se pudo obtener la relación para eliminar.');
        return;
    }
    const relaciones = await resp.json();
    const rel = relaciones.find(r => r.veterinario.id === veterinarioId && r.horario.id === horarioId);
    if (!rel) {
        alert('No se encontró la relación para eliminar.');
        return;
    }
    const respDel = await fetch(`/api/VeterinarioHorario/${rel.id}`, { method: 'DELETE' });
    if (!respDel.ok) {
        alert('Error al eliminar el horario.');
        return;
    }
    cargarHorarios();
}

// Editar horario (flujo preparado, implementación posterior)
function editarHorario(horarioId) {
    alert('Funcionalidad de edición en desarrollo.');
    // Aquí puedes abrir un modal o permitir edición en línea
}

// Inicializar
document.addEventListener('DOMContentLoaded', cargarHorarios); 