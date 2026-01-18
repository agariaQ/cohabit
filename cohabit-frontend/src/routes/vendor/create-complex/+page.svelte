<script>
    import { apiCall } from '$lib/api';
    import { goto } from '$app/navigation';

    let dto = {
        name: '', streetName: '', houseNumber: '',
        zipCode: '', city: 'Wien', district: '',
        location: { latitude: 48.2, longitude: 16.3 }
    };

    async function submit() {
        try {
            await apiCall('complex', '/api/v1/catalog/complexes', 'POST', dto);
            alert('Wohnanlage erstellt!');
            goto('/vendor/dashboard');
        } catch (e) {
            alert('Fehler: ' + e.message);
        }
    }
</script>

<div class="container">
    <h2>Neue Wohnanlage</h2>
    <form on:submit|preventDefault={submit}>
        <div class="form-group">
            <label>Name der Anlage</label>
            <input type="text" bind:value={dto.name} required placeholder="z.B. Donau-View" />
        </div>

        <div class="row">
            <div class="form-group">
                <label>Straße</label>
                <input type="text" bind:value={dto.streetName} required />
            </div>
            <div class="form-group sm">
                <label>Nr.</label>
                <input type="text" bind:value={dto.houseNumber} required />
            </div>
        </div>

        <div class="row">
            <div class="form-group sm">
                <label>PLZ</label>
                <input type="text" bind:value={dto.zipCode} required />
            </div>
            <div class="form-group">
                <label>Bezirk</label>
                <input type="text" bind:value={dto.district} placeholder="z.B. Leopoldstadt" />
            </div>
        </div>

        <button type="submit" class="btn-save">Anlage Speichern</button>
    </form>
</div>

<style>
    .container { max-width: 600px; margin: 40px auto; background: white; padding: 30px; border-radius: 12px; }
    .form-group { margin-bottom: 15px; }
    .form-group label { display: block; margin-bottom: 5px; font-weight: bold; font-size: 0.9rem; }
    input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 6px; }
    .row { display: flex; gap: 15px; }
    .sm { flex: 1; } .form-group { flex: 3; }
    .btn-save { width: 100%; background: #d35400; color: white; padding: 12px; border: none; border-radius: 6px; font-weight: bold; cursor: pointer; margin-top: 10px;}
</style>